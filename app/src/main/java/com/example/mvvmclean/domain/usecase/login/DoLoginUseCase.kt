package com.example.mvvmclean.domain.usecase.login

import com.example.mvvmclean.config.network.Resource
import com.example.mvvmclean.domain.model.auth.LoginRequest
import com.example.mvvmclean.domain.model.auth.LoginResponse
import com.example.mvvmclean.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DoLoginUseCase @Inject constructor(val repository: AuthRepository) {
    suspend fun execute(request: LoginRequest): Flow<Resource<LoginResponse>> =
        repository.postLogin(request)
}