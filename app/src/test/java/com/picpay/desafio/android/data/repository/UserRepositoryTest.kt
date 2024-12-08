package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.local.FakeDao
import com.picpay.desafio.android.data.local.UserDao
import com.picpay.desafio.android.data.remote.FakePicPayApi
import com.picpay.desafio.android.data.remote.PicPayApi
import com.picpay.desafio.android.di.testModule
import com.picpay.desafio.android.domain.repository.UserRepository
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
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryTest : KoinTest {

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        startKoin {
            modules(testModule)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin()
    }

    @Test
    fun dataIsRequestedFromRepo_localCacheIsEmpty_getDataFromApiSuccessfully() = runTest {
        //Given: empty local cache
        val dao = get<UserDao>() as FakeDao
        dao.testScenario = FakeDao.TestScenario.EmptyCache

        //And: api returns valid data
        val api = get<PicPayApi>() as FakePicPayApi
        api.testScenario = FakePicPayApi.TestScenario.Success

        val repository = get<UserRepository>()

        //When: data is requested from repository
        val result = repository.loadUsers()

        //Then: api is called
        assert(api.apiCalled)
        //And: local cache is populated
        assert(dao.dataInserted)
        //And: valid data is returned
        assert(result.isNotEmpty())
    }

    @Test
    fun dataIsRequestedFromRepo_localCacheIsEmpty_getDataFromApiFails() = runTest {
        //Given: empty local cache
        val dao = get<UserDao>() as FakeDao
        dao.testScenario = FakeDao.TestScenario.EmptyCache

        //And: api returns valid data
        val api = get<PicPayApi>() as FakePicPayApi
        api.testScenario = FakePicPayApi.TestScenario.Failure

        val repository = get<UserRepository>()

        assertFailsWith<IllegalStateException>() {
            //When: data is requested from repository
            repository.loadUsers()
            //Then: api is called
            assert(api.apiCalled)
            //And: local cache is not populated
            assertFalse(dao.dataInserted)

            //And: exception is thrown
        }
    }

    @Test
    fun dataIsRequestedFromRepo_localCacheHasData_returnsDataFromCache() = runTest {
        //Given: populated local cache
        val dao = get<UserDao>() as FakeDao
        dao.testScenario = FakeDao.TestScenario.Success

        val api = get<PicPayApi>() as FakePicPayApi

        val repository = get<UserRepository>()

        //When: data is requested from repository
        val result = repository.loadUsers()

        //Then: api is not called
        assertFalse(api.apiCalled)

        //And: valid data is returned
        assert(result.isNotEmpty())
    }

    @Test
    fun dataIsRequestedFromRepo_localCacheIsPopulated_failsToGetDataFromCache() = runTest {
        //Given: data fails to be retrieved from cache
        val dao = get<UserDao>() as FakeDao
        dao.testScenario = FakeDao.TestScenario.Failure

        val repository = get<UserRepository>()

        assertFailsWith<IllegalStateException>() {
            //When: data is requested from repository
            repository.loadUsers()

            //Then: exception is thrown
        }
    }

    @Test
    fun dataIsRequestedFromRepo_localCacheEmpty_returnsDataWithNullFields() = runTest {
        //Given: empty local cache
        val dao = get<UserDao>() as FakeDao
        dao.testScenario = FakeDao.TestScenario.EmptyCache

        //And: api returns valid data
        val api = get<PicPayApi>() as FakePicPayApi
        api.testScenario = FakePicPayApi.TestScenario.SuccessWithNullFields

        val repository = get<UserRepository>()

        //When: data is requested from repository
        val result = repository.loadUsers()

        //Then: api is called
        assert(api.apiCalled)
        //And: local cache is populated
        assert(dao.dataInserted)
        //And: valid data is returned
        assert(result.isNotEmpty())
        //And: valid data contain null fields
        assert(result.any { it.img == null })
    }

    @Test
    fun dataIsRequestedFromRepo_localCacheHasData_returnsDataWithNullFields() = runTest {
        //Given: empty local cache
        val dao = get<UserDao>() as FakeDao
        dao.testScenario = FakeDao.TestScenario.SuccessWithNullFields

        val repository = get<UserRepository>()

        //When: data is requested from repository
        val result = repository.loadUsers()

        //And: valid data is returned
        assert(result.isNotEmpty())
        //And: valid data contain null fields
        assert(result.any { it.img == null })
    }
}
