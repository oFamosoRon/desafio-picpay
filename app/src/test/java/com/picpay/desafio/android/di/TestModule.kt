package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.local.FakeDao
import com.picpay.desafio.android.data.local.UserDao
import com.picpay.desafio.android.data.remote.FakePicPayApi
import com.picpay.desafio.android.data.remote.PicPayApi
import com.picpay.desafio.android.data.repository.UserRepositoryImpl
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.domain.use_case.GetUsersUseCase
import com.picpay.desafio.android.ui.users_list.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val testModule = module {
    single<PicPayApi> { FakePicPayApi() }
    single<UserDao> { FakeDao() }
    single<UserRepository> {
        UserRepositoryImpl(
            api = get(),
            dao = get()
        )
    }
    single { GetUsersUseCase(get()) }
    viewModel { UsersViewModel(get()) }
}