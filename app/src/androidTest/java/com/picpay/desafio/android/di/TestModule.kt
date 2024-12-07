package com.picpay.desafio.android.di

import com.picpay.desafio.android.ui.users_list.UiState
import com.picpay.desafio.android.ui.users_list.UsersViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mockedViewModelUiState = MutableStateFlow<UiState>(UiState.Loading)

val testModule = module {

    viewModel<UsersViewModel> {
        mockk(relaxed = true) {
            every { uiState } returns mockedViewModelUiState
        }
    }
}