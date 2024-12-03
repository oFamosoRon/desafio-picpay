package com.picpay.desafio.android.data

import com.picpay.desafio.android.domain.User
import com.picpay.desafio.android.domain.UserRepository

class UserRepositoryImpl(
    private val api: PicPayApi
) : UserRepository {
    override suspend fun getUsers(): List<User> {
        val response = api.getUsers().execute()
        return if (response.isSuccessful) {
            val result = response.body()?.let { users ->
                users.map { it.toDomain() }
            } ?: emptyList<User>()
            result
        } else {
            emptyList()
        }
    }

    private fun UserDto.toDomain(): User {
        return User(
            img = img,
            name = name,
            id = id,
            username = username
        )
    }
}