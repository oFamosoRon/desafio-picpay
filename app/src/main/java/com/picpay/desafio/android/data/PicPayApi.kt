package com.picpay.desafio.android.data

import com.picpay.desafio.android.domain.User
import retrofit2.Call
import retrofit2.http.GET

interface PicPayApi {

    @GET("users")
    fun getUsers(): Call<List<User>>
}