package com.picpay.desafio.android.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PicPayService {
    private const val BASE_URL = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"

    private val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val api: PicPayApi = retrofit.create(PicPayApi::class.java)
}