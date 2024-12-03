package com.picpay.desafio.android.domain

interface UserRepository {
    suspend fun getUsers(): List<User>
}