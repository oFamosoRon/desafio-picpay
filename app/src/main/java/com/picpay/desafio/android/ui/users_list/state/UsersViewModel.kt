package com.picpay.desafio.android.ui.users_list.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.ui.users_list.view.UiAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsersViewModel(
    private val strategies: Map<UiAction, UpdateStateStrategy>
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        handleAction(UiAction.LoadUsers)
    }

    fun handleAction(action: UiAction) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val strategy = strategies[action]
            _uiState.value = strategy?.execute() ?: UiState.Error("Unknown action")
        }
    }
}
