package com.picpay.desafio.android.ui.users_list.state

interface UpdateStateStrategy {
    suspend fun execute(): UiState
}