package com.picpay.desafio.android.domain.use_case

import com.picpay.desafio.android.core.ResultWrapper
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository

class RefreshUsersUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): ResultWrapper<List<User>> {
        return try {
            val users = userRepository.refreshUsers()
            ResultWrapper.Success(users)
        } catch (e: Exception) {
            ResultWrapper.Error(e.message)
        }
    }
}
