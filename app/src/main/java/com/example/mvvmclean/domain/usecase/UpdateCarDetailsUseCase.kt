package com.example.mvvmclean.domain.usecase

import com.example.mvvmclean.domain.model.Car
import com.example.mvvmclean.domain.repository.CarRepository

class UpdateCarDetailsUseCase(private val repository: CarRepository) {
    suspend fun execute(car: Car) = repository.updateCarDetails(car)
}