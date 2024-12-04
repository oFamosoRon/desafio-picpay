package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.local.UserDao
import com.picpay.desafio.android.data.local.UserEntity
import com.picpay.desafio.android.data.remote.UserDto
import com.picpay.desafio.android.data.remote.PicPayApi
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository

class UserRepositoryImpl(
    private val api: PicPayApi,
    private val dao: UserDao
) : UserRepository {
    override suspend fun getUsers(): List<User> {

        val users = dao.getAllUsers()

        if (users.isNotEmpty()) {
            return users.map { it.toDomain() }
        }

        val usersFromApi = api.getUsers().map { it.toDomain() }

        usersFromApi
            .map { it.toEntity() }
            .forEach { userEntity ->
                dao.insertUser(userEntity)
            }

        return usersFromApi
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