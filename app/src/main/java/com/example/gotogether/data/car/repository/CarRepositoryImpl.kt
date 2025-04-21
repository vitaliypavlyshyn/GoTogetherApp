package com.example.gotogether.data.car.repository

import com.example.gotogether.data.car.CarApiService
import com.example.gotogether.data.car.toDomainList
import com.example.gotogether.data.location.LocationApiService
import com.example.gotogether.data.location.repository.LocationRepository
import com.example.gotogether.data.location.toDomainList
import com.example.gotogether.domain.car.Car
import com.example.gotogether.domain.location.Location
import javax.inject.Inject

class CarRepositoryImpl @Inject constructor(
    private val api: CarApiService
) : CarRepository{
    override suspend fun getCars(): Result<List<Car>> {
        return try {
            val cars = api.getCars()
            Result.success(cars.toDomainList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}