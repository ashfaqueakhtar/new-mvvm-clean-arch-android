package com.example.mvvmclean.presentation.details.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmclean.domain.model.Car
import com.example.mvvmclean.domain.usecase.GetCarDetailsUseCase
import com.example.mvvmclean.domain.usecase.UpdateCarDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarDetailViewModel @Inject constructor(
    private val getCarDetailsUseCase: GetCarDetailsUseCase,
    private val updateCarDetailsUseCase: UpdateCarDetailsUseCase
) : ViewModel() {

    private val _carDetails = MutableStateFlow<Car?>(null)
    val carDetails: StateFlow<Car?> = _carDetails.asStateFlow()

    fun fetchCarDetails(carId: Int) {
        viewModelScope.launch {
            _carDetails.value = getCarDetailsUseCase.execute(carId)
        }
    }

    fun updateCarDetails(updatedCar: Car) {
        viewModelScope.launch {
            updateCarDetailsUseCase.execute(updatedCar)
            _carDetails.value = updatedCar
            //Log.d("TAG", "${updatedCar.name} ${updatedCar.brand} ${updatedCar.price}")
            //Log data should not be present if not mocked
        }
    }
}