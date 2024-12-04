package com.picpay.desafio.android.data

import com.picpay.desafio.android.domain.User
import com.picpay.desafio.android.domain.UserRepository

class UserRepositoryImpl(
    private val api: PicPayApi
) : UserRepository {
    override suspend fun getUsers(): List<User> {
        return api.getUsers().map { it.toDomain() }
    }

    private fun UserDto.toDomain(): User {
        return User(
            id = id,
            img = img,
            name = name,
            username = username
        )
    }
}