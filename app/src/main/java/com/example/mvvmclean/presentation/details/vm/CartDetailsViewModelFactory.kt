package com.example.mvvmclean.presentation.details.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmclean.domain.usecase.GetCarDetailsUseCase
import com.example.mvvmclean.domain.usecase.UpdateCarDetailsUseCase
import java.lang.IllegalArgumentException

class CartDetailsViewModelFactory(
    private val getCarDetailsUseCase: GetCarDetailsUseCase,
    private val updateCarDetailsUseCase: UpdateCarDetailsUseCase
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CarDetailViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return CarDetailViewModel(
                getCarDetailsUseCase, updateCarDetailsUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}