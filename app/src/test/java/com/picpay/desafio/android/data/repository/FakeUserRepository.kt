package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository

class FakeUserRepository : UserRepository {

    var testScenario: TestScenario = TestScenario.Success

    override suspend fun getUsers(): List<User> {
        return when (testScenario) {
            is TestScenario.Success -> users

            is TestScenario.Failure -> throw IllegalStateException("invalid data")
        }
    }

    sealed class TestScenario {
        object Success : TestScenario()
        object Failure : TestScenario()
    }
}