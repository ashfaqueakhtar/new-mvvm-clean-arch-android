package com.example.mvvmclean.config.network

sealed class Resource<T>(
    val status: Status? = null,
    val data: T? = null,
    val message: String? = null,
    val statusCode: Int? = null,
) {
    class Success<T>(data: T? = null, message: String? = null) :
        Resource<T>(status = Status.SUCCESS, data = data, message = message)

    class Error<T>(data: T? = null , message: String? = null, statusCode: Int? = null) :
        Resource<T>(status = Status.ERROR, data = data, message = message, statusCode = statusCode)

    class Loading<T>() :
        Resource<T>(status = Status.LOADING, data = null, message = null)
}