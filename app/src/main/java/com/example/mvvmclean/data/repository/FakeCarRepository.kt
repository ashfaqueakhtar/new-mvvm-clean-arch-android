package com.example.mvvmclean.data.repository

import com.example.mvvmclean.domain.model.Car
import com.example.mvvmclean.domain.repository.CarRepository

class FakeCarRepository : CarRepository {
    private val cars = mutableListOf(
        Car(1, "Model S", "Tesla", "$89,990", "https://via.placeholder.com/150"),
        Car(2, "Mustang", "Ford", "$55,300", "https://via.placeholder.com/150"),
        Car(3, "Civic", "Honda", "$25,000", "https://via.placeholder.com/150"),
        Car(4, "Corolla", "Toyota", "$20,000", "https://via.placeholder.com/150")
    )

    override suspend fun getCarList(): List<Car> = cars

    override suspend fun getCarDetails(carId: Int): Car = cars.first { it.id == carId }

    override suspend fun updateCarDetails(car: Car) {
        val index = cars.indexOfFirst { it.id == car.id }
        if (index != -1) cars[index] = car
    }
}