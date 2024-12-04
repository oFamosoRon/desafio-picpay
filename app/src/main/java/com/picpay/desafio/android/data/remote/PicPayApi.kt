package com.picpay.desafio.android.data.remote

import retrofit2.http.GET

interface PicPayApi {

    @GET("users")
    suspend fun getUsers(): List<UserDto>
}