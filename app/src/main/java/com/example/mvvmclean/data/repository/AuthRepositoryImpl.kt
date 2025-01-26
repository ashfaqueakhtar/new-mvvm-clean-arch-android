package com.example.mvvmclean.data.repository

import com.example.mvvmclean.config.network.ApiException
import com.example.mvvmclean.config.network.ApiService
import com.example.mvvmclean.config.network.Resource
import com.example.mvvmclean.domain.model.auth.LoginRequest
import com.example.mvvmclean.domain.model.auth.LoginResponse
import com.example.mvvmclean.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(private val apiService: ApiService) : AuthRepository {
    override suspend fun postLogin(request: LoginRequest): Flow<Resource<LoginResponse>> =
        flow<Resource<LoginResponse>> {
            emit(Resource.Loading())
            try {
                val response = apiService.postLogin(request = request)
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(Resource.Success(data = response.body(), message = response.message()))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()?:""
                    emit(Resource.Error(data = null, message = errorBody))
                }
            } catch (e: Exception) {
                val apiException = ApiException(e)
                emit(Resource.Error(message = apiException.message))
            }
        }.flowOn(Dispatchers.IO)
}