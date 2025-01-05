package com.example.mvvmclean.presentation.list.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmclean.domain.model.Car
import com.example.mvvmclean.domain.usecase.GetCarListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class CarListViewModel constructor(
    private val getCarListUseCase: GetCarListUseCase
) : ViewModel() {
    private val _carList = MutableStateFlow<List<Car>>(emptyList())
    val carList: StateFlow<List<Car>> = _carList.asStateFlow()

    init {
        viewModelScope.launch {
            _carList.value = getCarListUseCase.execute()
        }
    }
}