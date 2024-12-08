package com.picpay.desafio.android.di

import android.content.Context
import androidx.room.Room
import com.picpay.desafio.android.data.local.AppDatabase
import com.picpay.desafio.android.data.local.UserDao
import org.koin.dsl.module

private fun provideDataBase(context: Context): AppDatabase =
    Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "database"
    ).fallbackToDestructiveMigration().build()

private fun provideUserDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao

val databaseModule = module {
    single { provideDataBase(get()) }
    single { provideUserDao(get()) }
}
