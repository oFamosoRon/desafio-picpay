package com.picpay.desafio.android.data.local

class FakeDao : UserDao {

    var testScenario: TestScenario = TestScenario.Success

    var dataInserted = false
        private set

    override suspend fun getUserById(id: Int): UserEntity? {
        return when (testScenario) {
            TestScenario.Success -> users[0]
            TestScenario.SuccessWithNullFields -> usersWithNullFields[0]
            TestScenario.SuccessWithNullUser -> null
            else -> throw IllegalStateException("invalid data")
        }
    }

    override suspend fun getAllUsers(): List<UserEntity> {
        return when (testScenario) {
            TestScenario.Success -> users
            TestScenario.SuccessWithNullFields -> usersWithNullFields
            TestScenario.EmptyCache -> emptyList<UserEntity>()
            else -> throw IllegalStateException("invalid data")
        }
    }

    override suspend fun insertUser(user: UserEntity) {
        dataInserted = true
    }

    override suspend fun updateUser(user: UserEntity) = Unit

    override suspend fun deleteAllUsers() = Unit

    override suspend fun deleteUserById(id: Int) = Unit

    sealed class TestScenario {
        object Success : TestScenario()
        object EmptyCache : TestScenario()
        object SuccessWithNullUser : TestScenario()
        object SuccessWithNullFields : TestScenario()
        object Failure : TestScenario()
    }

    private val users = listOf(
        UserEntity(
            id = 1,
            name = "Roney Aguiar",
            username = "oFamosoRon",
            img = "https://profile-pic.com/img.png"
        ),
        UserEntity(
            id = 2,
            name = "Beatriz Almeida",
            username = "bialmeida",
            img = "https://profile-pic.com/img.png"
        ),
        UserEntity(
            id = 3,
            name = "Andre Aguiar",
            username = "oFamosoAnd",
            img = "https://profile-pic.com/img.png"
        )
    )

    private val usersWithNullFields = listOf(
        UserEntity(
            id = 1,
            name = "Roney Aguiar",
            username = "oFamosoRon",
            img = "https://profile-pic.com/img.png"
        ),
        UserEntity(
            id = 2,
            name = null,
            username = null,
            img = null
        ),
        UserEntity(
            id = 3,
            name = null,
            username = null,
            img = null
        )
    )
}