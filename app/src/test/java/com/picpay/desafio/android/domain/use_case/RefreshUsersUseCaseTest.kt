package com.picpay.desafio.android.domain.use_case

import com.picpay.desafio.android.core.ResultWrapper
import com.picpay.desafio.android.data.local.FakeDao
import com.picpay.desafio.android.data.local.UserDao
import com.picpay.desafio.android.data.remote.FakePicPayApi
import com.picpay.desafio.android.data.remote.PicPayApi
import com.picpay.desafio.android.di.testModule
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get

@OptIn(ExperimentalCoroutinesApi::class)
class RefreshUsersUseCaseTest : KoinTest {

    private lateinit var useCase: RefreshUsersUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        startKoin {
            modules(testModule)
        }

        useCase = get<RefreshUsersUseCase>()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin()
    }

    @Test
    fun useCasesIsInvoked_returnsSuccess() = runTest {

        //When: use case is invoked
        val result = useCase()

        //Then: it returns success
        assert(result is ResultWrapper.Success)
        //And: it contains valid data
        assert((result as ResultWrapper.Success).data.isNotEmpty())
    }

    @Test
    fun useCasesIsInvoked_returnsError() = runTest {
        //Given: some error happens during the data loading
        val api = get<PicPayApi>() as FakePicPayApi
        api.testScenario = FakePicPayApi.TestScenario.Failure

        val dao = get<UserDao>() as FakeDao
        dao.testScenario = FakeDao.TestScenario.Failure

        //When: use case is invoked
        val result = useCase()

        //Then: it returns error
        assert(result is ResultWrapper.Error)
        //And: it contains some error message
        assertFalse((result as ResultWrapper.Error).message.isNullOrBlank())
    }
}
