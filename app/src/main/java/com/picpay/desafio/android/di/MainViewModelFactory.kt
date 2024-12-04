package com.picpay.desafio.android.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.picpay.desafio.android.domain.GetUsersUseCase
import com.picpay.desafio.android.domain.UserRepository
import com.picpay.desafio.android.ui.MainViewModel

class MainViewModelFactory(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(getUsersUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}