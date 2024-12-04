package com.picpay.desafio.android.domain.use_case

import com.picpay.desafio.android.core.ResultWrapper
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.domain.model.User

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