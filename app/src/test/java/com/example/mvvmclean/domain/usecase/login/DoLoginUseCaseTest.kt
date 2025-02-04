package com.example.mvvmclean.domain.usecase.login

import com.example.mvvmclean.config.network.Resource
import com.example.mvvmclean.config.network.Status
import com.example.mvvmclean.domain.model.auth.LoginRequest
import com.example.mvvmclean.domain.model.auth.LoginResponse
import com.example.mvvmclean.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class DoLoginUseCaseTest{
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var authRepository: AuthRepository

    //private lateinit var loginRequest: LoginRequest

    private lateinit var doLoginUseCase: DoLoginUseCase

    @Before
    fun setUp(){
        doLoginUseCase = DoLoginUseCase(authRepository)
    }



    @Test
    fun `execute_input_correctCredentials_expected_Success`() = runTest{
        val inputLoginRequest = LoginRequest(
            email = "test@example.com",
            password = "password123"
        )

        val mockLoginResponse = LoginResponse(
            token = "someToken"
        )

        val mockResourceFlow : Flow<Resource<LoginResponse>> = flowOf(
            Resource.Success(data = mockLoginResponse)
        )

        // Define mock repository behavior
        `when`(authRepository.postLogin(inputLoginRequest))
            .thenReturn(mockResourceFlow)

        //execute
        val resultFlow = doLoginUseCase.execute(inputLoginRequest)

        // Collect and verify flow emissions
        val results = resultFlow.toList()

        // Verify interactions
        verify(authRepository, times(1)).postLogin(inputLoginRequest)

        //test check flow size
        assertEquals(1,results.size)

        //
        assertEquals(Status.SUCCESS,results[0].status)
        assertEquals(mockLoginResponse,results[0].data)
    }

    @Test
    fun `execute_input_wrongCredentials_expected_Error`() = runTest{

        val inputLoginRequest = LoginRequest(
            email = "error@example.com",
            password = "wrongpassword"
        )

        val mockErrorFlow: Flow<Resource<LoginResponse>> = flowOf(
            Resource.Loading(),
            Resource.Error(message = "Invalid credentials")
        )

        // Define mock repository behavior
        `when`(
            authRepository.postLogin(inputLoginRequest)
        ).thenReturn(mockErrorFlow)


        // Execute
        val resultFlow = doLoginUseCase.execute(inputLoginRequest)


        val results = resultFlow.toList()

        // Verify interactions
        verify(authRepository, times(1)).postLogin(inputLoginRequest)

        //test check flow size
        assertEquals(2,results.size)

        //When Loading
        assertEquals(Status.LOADING,results[0].status)
        assertEquals(null,results[0].data)

        //SUCCESS
        assertEquals(Status.ERROR,results[1].status)
        assertEquals("Invalid credentials",results[1].message)
    }
}