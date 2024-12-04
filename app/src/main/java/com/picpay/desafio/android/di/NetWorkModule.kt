package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.remote.PicPayApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    val BASE_URL = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"

    single {
        Interceptor { chain ->
            val request: Request = chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer your_token_here")
                .addHeader("Accept", "application/json")
                .build()
            chain.proceed(request)
        }
    }

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<Interceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<PicPayApi> {
        get<Retrofit>().create(PicPayApi::class.java)
    }
}