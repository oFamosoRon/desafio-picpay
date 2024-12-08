package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.local.FakeDao
import com.picpay.desafio.android.data.local.UserDao
import com.picpay.desafio.android.data.remote.FakePicPayApi
import com.picpay.desafio.android.data.remote.PicPayApi
import com.picpay.desafio.android.data.repository.UserRepositoryImpl
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.domain.use_case.LoadUsersUseCase
import com.picpay.desafio.android.domain.use_case.RefreshUsersUseCase
import com.picpay.desafio.android.ui.users_list.state.LoadUsersStrategy
import com.picpay.desafio.android.ui.users_list.state.RefreshUsersStrategy
import com.picpay.desafio.android.ui.users_list.state.UsersViewModel
import com.picpay.desafio.android.ui.users_list.view.UiAction
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
    single { LoadUsersUseCase(get()) }
    single { RefreshUsersUseCase(get()) }

    single {
        mapOf(
            UiAction.LoadUsers to LoadUsersStrategy(get()),
            UiAction.RefreshUsers to RefreshUsersStrategy(get())
        )
    }

    viewModel { UsersViewModel(get()) }
}