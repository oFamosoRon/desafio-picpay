package com.picpay.desafio.android.domain.repository

import com.picpay.desafio.android.domain.model.User

interface UserRepository {
    suspend fun loadUsers(): List<User>
    suspend fun refreshUsers(): List<User>
}