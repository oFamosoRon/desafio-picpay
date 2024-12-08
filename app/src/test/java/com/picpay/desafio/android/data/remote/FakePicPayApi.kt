package com.picpay.desafio.android.data.remote

class FakePicPayApi : PicPayApi {

    var testScenario: TestScenario = TestScenario.Success

    var apiCalled = false
        private set

    override suspend fun getUsers(): List<UserDto> {
        apiCalled = true
        return when (testScenario) {
            is TestScenario.Success -> users
            is TestScenario.SuccessEmptyList -> emptyList()
            is TestScenario.SuccessWithNullFields -> usersWithNullFields
            is TestScenario.Failure -> throw IllegalStateException("invalid data")
        }
    }

    sealed class TestScenario {
        object Success : TestScenario()
        object SuccessEmptyList : TestScenario()
        object SuccessWithNullFields : TestScenario()
        object Failure : TestScenario()
    }

    private val users = listOf(
        UserDto(
            id = 1,
            name = "Roney Aguiar",
            username = "oFamosoRon",
            img = "https://profile-pic.com/img.png"
        ),
        UserDto(
            id = 2,
            name = "Beatriz Almeida",
            username = "bialmeida",
            img = "https://profile-pic.com/img.png"
        ),
        UserDto(
            id = 3,
            name = "Andre Aguiar",
            username = "oFamosoAnd",
            img = "https://profile-pic.com/img.png"
        )
    )

    private val usersWithNullFields = listOf(
        UserDto(
            id = 1,
            name = "Roney Aguiar",
            username = "oFamosoRon",
            img = "https://profile-pic.com/img.png"
        ),
        UserDto(
            id = 2,
            name = null,
            username = null,
            img = null
        ),
        UserDto(
            id = 3,
            name = null,
            username = null,
            img = null
        )
    )
}
