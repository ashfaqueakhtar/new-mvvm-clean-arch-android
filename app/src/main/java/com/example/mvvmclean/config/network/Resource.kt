package com.example.mvvmclean.config.network

sealed class Resource<T>(
    val status: Status? = null,
    val data: T? = null,
    val message: String? = null,
    val statusCode: Int? = null,
) {
    class Success<T>(data: T?, message: String?) :
        Resource<T>(status = Status.SUCCESS, data = data, message = message)

    class Error<T>(data: T?, message: String?, statusCode: Int?) :
        Resource<T>(status = Status.ERROR, data = data, message = message, statusCode = statusCode)

    class Loading<T>(data: T?) : Resource<T>(status = Status.LOADING, data = data, message = null)
}