package com.example.mvvmclean.presentation.login

import com.example.mvvmclean.config.network.Resource
import com.example.mvvmclean.config.network.Status
import com.example.mvvmclean.domain.model.auth.LoginRequest
import com.example.mvvmclean.domain.model.auth.LoginResponse
import com.example.mvvmclean.domain.usecase.login.DoLoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val mockitoRule : MockitoRule= MockitoJUnit.rule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var loginViewModel: LoginViewModel

    @Mock
    private lateinit var doLoginUseCase: DoLoginUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        loginViewModel = LoginViewModel(doLoginUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `test initial status`() = runTest {
        val initialStatus = loginViewModel.status.first()
        assertEquals(Status.IDLE, initialStatus)
    }
    @Test
    fun `loginApiCall_inputApiCall_output_changeStatus`() = runTest{

        val inputLoginRequest = LoginRequest(
            email = "error@example.com",
            password = "wrongpassword"
        )

        val mockResourceFlow : Flow<Resource<LoginResponse>> = flowOf(
            Resource.Loading(),
            Resource.Success()
        )

        `when`(doLoginUseCase.execute(inputLoginRequest))
            .thenReturn(mockResourceFlow)

        //execute
        val resultFlow = doLoginUseCase.execute(inputLoginRequest)

        // Collect and verify flow emissions
        val results = resultFlow.toList()

        assertEquals(Status.LOADING, results[0].status)
        assertEquals(Status.SUCCESS, results[1].status)

    }
}