package com.example.mvvmclean.domain.repository

import com.example.mvvmclean.domain.model.Car


interface CarRepository {
    suspend fun getCarList(): List<Car>
    suspend fun getCarDetails(carId: Int): Car
    suspend fun updateCarDetails(car: Car)
}