package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.repository.UserRepositoryImpl
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.domain.use_case.GetUsersUseCase
import com.picpay.desafio.android.ui.users_list.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<UserRepository> {
        UserRepositoryImpl(
            api = get(),
            dao = get()
        )
    }
    single { GetUsersUseCase(get()) }
    viewModel { UsersViewModel(get()) }

}
