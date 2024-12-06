package com.picpay.desafio.android.ui.users_list

import app.cash.turbine.test
import com.picpay.desafio.android.data.repository.FakeUserRepository
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
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class UsersViewModelTest : KoinTest {

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
    fun viewModelIsInstantiated_stateStartsToBeUpdated_stateStopsUpdatingOnSuccess() = runTest {
        //Given
        val viewModel = get<UsersViewModel>()

        //When: the view model uiState starts being collected
        viewModel.uiState.test {
            //Then: the initial state is Loading
            assertIs<UiState.Loading>(awaitItem())
            //And: the next and final state is success
            assertIs<UiState.Success>(awaitItem())
        }
    }

    @Test
    fun viewModelIsInstantiated_stateStartsToBeUpdated_stateStopsUpdatingOnError() = runTest {
        //Given: an error happens during the data loading
        val repository = get<UserRepository>() as FakeUserRepository
        repository.testScenario = FakeUserRepository.TestScenario.Failure

        val viewModel = get<UsersViewModel>()

        //When: the view model uiState starts being collected
        viewModel.uiState.test {
            //Then: the initial state is Loading
            assertIs<UiState.Loading>(awaitItem())
            //And: the next and final state is Error
            assertIs<UiState.Error>(awaitItem())
        }
    }
}