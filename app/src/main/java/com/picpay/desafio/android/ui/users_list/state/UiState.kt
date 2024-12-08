package com.picpay.desafio.android.ui.users_list.state

import com.picpay.desafio.android.domain.model.User

sealed class UiState {
    object Loading : UiState()
    data class Success(val users: List<User>) : UiState()
    data class Error(val message: String?) : UiState()
}