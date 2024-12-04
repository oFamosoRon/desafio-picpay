package com.picpay.desafio.android.data

import retrofit2.http.GET

interface PicPayApi {

    @GET("users")
    suspend fun getUsers(): List<UserDto>
}