package com.picpay.desafio.android.ui.users_list.state

import com.picpay.desafio.android.core.ResultWrapper
import com.picpay.desafio.android.domain.use_case.LoadUsersUseCase

class LoadUsersStrategy(
    private val loadUsersUseCase: LoadUsersUseCase
) : UpdateStateStrategy {
    override suspend fun execute(): UiState {
        return when (val result = loadUsersUseCase()) {
            is ResultWrapper.Success -> UiState.Success(users = result.data)
            is ResultWrapper.Error -> UiState.Error(result.message)
        }
    }
}
