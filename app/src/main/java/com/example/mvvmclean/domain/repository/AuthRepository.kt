package com.example.mvvmclean.domain.repository

import com.example.mvvmclean.config.network.Resource
import com.example.mvvmclean.domain.model.Car
import com.example.mvvmclean.domain.model.auth.LoginRequest
import com.example.mvvmclean.domain.model.auth.LoginResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body

interface AuthRepository {
    suspend fun postLogin(request: LoginRequest): Flow<Resource<LoginResponse>>

}