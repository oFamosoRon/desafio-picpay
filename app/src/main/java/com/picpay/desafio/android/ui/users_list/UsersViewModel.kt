package com.picpay.desafio.android.ui.users_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.core.ResultWrapper
import com.picpay.desafio.android.domain.use_case.GetUsersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsersViewModel(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch {
            when (val result = getUsersUseCase()) {
                is ResultWrapper.Success -> {
                    _uiState.value = UiState.Success(users = result.data)
                }

                is ResultWrapper.Error -> {
                    _uiState.value = UiState.Error(result.message)
                }
            }
        }
    }
}