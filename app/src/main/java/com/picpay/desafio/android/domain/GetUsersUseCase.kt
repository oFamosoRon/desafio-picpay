package com.picpay.desafio.android.domain

import com.picpay.desafio.android.core.ResultWrapper

class GetUsersUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): ResultWrapper<List<User>> {
        return try {
            val users = userRepository.getUsers()
            ResultWrapper.Success(users)
        } catch (e: Exception) {
            ResultWrapper.Error(e.message)
        }
    }
}