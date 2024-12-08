package com.picpay.desafio.android.ui.users_list.state

import com.picpay.desafio.android.core.ResultWrapper
import com.picpay.desafio.android.domain.use_case.RefreshUsersUseCase


class RefreshUsersStrategy(
    private val refreshUsersUseCase: RefreshUsersUseCase
) : UpdateStateStrategy {
    override suspend fun execute(): UiState {

        return when (val result = refreshUsersUseCase()) {
            is ResultWrapper.Success -> UiState.Success(users = result.data)
            is ResultWrapper.Error -> UiState.Error(result.message)
        }

    }
}