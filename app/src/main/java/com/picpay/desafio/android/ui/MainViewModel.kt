package com.picpay.desafio.android.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.core.ResultWrapper
import com.picpay.desafio.android.domain.GetUsersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
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