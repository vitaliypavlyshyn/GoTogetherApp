package com.example.gotogether.data.car.repository

import com.example.gotogether.domain.car.Car

interface CarRepository {
    suspend fun getCars(): Result<List<Car>>
}