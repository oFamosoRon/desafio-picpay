package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.di.testModule
import org.koin.core.context.startKoin

class TestApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(testModule)
        }
    }
}