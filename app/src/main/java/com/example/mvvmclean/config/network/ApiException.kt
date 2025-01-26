package com.example.mvvmclean.config.network

import okio.IOException
import retrofit2.HttpException
import java.net.SocketException

class ApiException(throwable: Throwable) : Exception() {
    override val message: String =
        when (throwable) {
            is SocketException -> {
                "No Internet connection"
            }


            is IOException -> when {
                throwable.message?.contains("No address associated with hostname") == true -> "No internet connection"
                throwable.message?.contains("timeout") == true -> "Connection timeout"
                else -> "Network error"
            }

            is HttpException -> when (throwable.code()) {
                400 -> "Bad Request"
                401 -> "Unauthorized"
                403 -> "Forbidden"
                404 -> "Not Found"
                422 -> "Validation Error"
                500 -> "Internal Server Error"
                502 -> "Bad Gateway"
                503 -> "Service Unavailable"
                else -> "HTTP Error: ${throwable.code()}"
            }

            else -> "Unexpected error occurred"
        }

    override fun toString(): String = message
}