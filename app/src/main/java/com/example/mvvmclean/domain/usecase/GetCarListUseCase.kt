package com.example.mvvmclean.domain.usecase

import com.example.mvvmclean.domain.model.Car
import com.example.mvvmclean.domain.repository.CarRepository
import javax.inject.Inject

class GetCarListUseCase  @Inject constructor(private val repository: CarRepository) {
    //Dependency injection with constructor
    suspend fun execute(): List<Car>? = repository.getCarList()
}