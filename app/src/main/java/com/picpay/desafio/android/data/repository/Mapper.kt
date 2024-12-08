package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.local.UserEntity
import com.picpay.desafio.android.data.remote.UserDto
import com.picpay.desafio.android.domain.model.User

fun UserDto.toDomain(): User {
    return User(
        id = id,
        img = img,
        name = name,
        username = username
    )
}

fun UserEntity.toDomain(): User {
    return User(
        id = id,
        img = img,
        name = name,
        username = username
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        img = img,
        name = name,
        username = username
    )
}
