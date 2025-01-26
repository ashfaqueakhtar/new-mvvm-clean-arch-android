package com.example.mvvmclean.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmclean.config.network.Status
import com.example.mvvmclean.domain.model.auth.LoginRequest
import com.example.mvvmclean.domain.usecase.login.DoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val doLoginUseCase: DoLoginUseCase
) : ViewModel() {


    fun loginApiCall(email: String = "aaaa@gamil.com", password: String = "abx") {
        viewModelScope.launch {
            val req = LoginRequest(email = email, password = password)
            doLoginUseCase.execute(req).collect() {
                when (it.status) {
                    Status.LOADING -> {
                        Log.d("LoginViewModel", "STATUS => LOADING")
                    }

                    Status.SUCCESS -> {
                        Log.d("LoginViewModel", "STATUS => SUCCESS")
                    }

                    Status.ERROR -> {
                        Log.d("LoginViewModel", "STATUS => ERROR")
                    }

                    else -> {

                    }
                }
            }
        }
    }
}