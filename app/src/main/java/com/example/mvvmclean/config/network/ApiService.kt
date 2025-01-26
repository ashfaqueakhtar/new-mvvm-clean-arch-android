package com.example.mvvmclean.config.network

import com.example.mvvmclean.domain.model.auth.LoginRequest
import com.example.mvvmclean.domain.model.auth.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST(NetworkConstants.endPointLogin)
    suspend fun postLogin(
        @Body request: LoginRequest
    ): Response<LoginResponse>

}