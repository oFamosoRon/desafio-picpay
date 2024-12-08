package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.local.UserDao
import com.picpay.desafio.android.data.local.UserEntity
import com.picpay.desafio.android.data.remote.PicPayApi
import com.picpay.desafio.android.data.remote.UserDto
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class UserRepositoryImpl(
    private val api: PicPayApi,
    private val dao: UserDao
) : UserRepository {

    private val mutex = Mutex()

    override suspend fun loadUsers(): List<User> {
        mutex.withLock {
            val users = dao.getAllUsers()

            if (users.isNotEmpty()) {
                return users.map { it.toDomain() }
            }

            val usersFromApi = api.getUsers().map { it.toDomain() }
            insertUsersAtLocalDatabase(usersFromApi)
            return usersFromApi
        }
    }

    override suspend fun refreshUsers(): List<User> {
        mutex.withLock {
            return try {
                val usersFromApi = api.getUsers().map { it.toDomain() }
                if (usersFromApi.isNotEmpty()) {
                    insertUsersAtLocalDatabase(usersFromApi)
                }

                usersFromApi
            } catch (e: Exception) {
                val users = dao.getAllUsers()
                users.map { it.toDomain() }
            }
        }
    }

    private suspend fun insertUsersAtLocalDatabase(usersFromApi: List<User>) {
        usersFromApi
            .map { it.toEntity() }
            .forEach { userEntity ->
                dao.insertUser(userEntity)
            }
    }

    private fun UserDto.toDomain(): User {
        return User(
            id = id,
            img = img,
            name = name,
            username = username
        )
    }

    private fun UserEntity.toDomain(): User {
        return User(
            id = id,
            img = img,
            name = name,
            username = username
        )
    }

    private fun User.toEntity(): UserEntity {
        return UserEntity(
            id = id,
            img = img,
            name = name,
            username = username
        )
    }
}