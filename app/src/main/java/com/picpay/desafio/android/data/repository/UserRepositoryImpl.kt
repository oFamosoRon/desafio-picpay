package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.model.UserDto
import com.picpay.desafio.android.data.remote.PicPayApi
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository

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