package com.picpay.desafio.android.core

sealed class ResultWrapper<T> {
    data class Success<T>(val data: T) : ResultWrapper<T>()
    data class Error<T>(val message: String?) : ResultWrapper<T>()
}
