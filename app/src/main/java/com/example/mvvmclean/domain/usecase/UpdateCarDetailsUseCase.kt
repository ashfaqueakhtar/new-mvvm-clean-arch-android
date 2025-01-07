package com.example.mvvmclean.domain.usecase

import com.example.mvvmclean.domain.model.Car
import com.example.mvvmclean.domain.repository.CarRepository
import javax.inject.Inject

class UpdateCarDetailsUseCase @Inject constructor(private val repository: CarRepository) {
    suspend fun execute(car: Car?) = repository.updateCarDetails(car)
}