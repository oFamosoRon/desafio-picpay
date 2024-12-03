package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.PicPayApi
import com.picpay.desafio.android.data.UserRepositoryImpl
import com.picpay.desafio.android.domain.UserRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserRepositoryProvider {
    private const val BASE_URL = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"

    private val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val api: PicPayApi = retrofit.create(PicPayApi::class.java)

    val userRepository: UserRepository by lazy {
        UserRepositoryImpl(api)
    }
}