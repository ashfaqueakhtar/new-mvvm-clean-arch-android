package com.example.mvvmclean.domain.usecase

import com.example.mvvmclean.domain.model.Car
import com.example.mvvmclean.domain.repository.CarRepository

class GetCarDetailsUseCase(private val repository: CarRepository) {
    suspend fun execute(carId: Int): Car = repository.getCarDetails(carId)
}