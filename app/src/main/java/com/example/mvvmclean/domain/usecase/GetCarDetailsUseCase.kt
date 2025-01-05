package com.example.mvvmclean.domain.usecase

import com.example.mvvmclean.domain.model.Car
import com.example.mvvmclean.domain.repository.CarRepository
import javax.inject.Inject


class GetCarDetailsUseCase @Inject constructor(private val repository: CarRepository) {
    suspend fun execute(carId: Int): Car = repository.getCarDetails(carId)
}