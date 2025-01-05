package com.example.mvvmclean.presentation.list.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmclean.domain.usecase.GetCarListUseCase

class CartListViewModelFactory(
    private val getCarListUseCase: GetCarListUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarListViewModel(getCarListUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}