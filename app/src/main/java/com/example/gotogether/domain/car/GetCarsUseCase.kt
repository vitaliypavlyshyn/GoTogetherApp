package com.example.gotogether.domain.car

import com.example.gotogether.data.car.repository.CarRepository
import javax.inject.Inject

class GetCarsUseCase @Inject constructor(
    private val repository: CarRepository
) {
    suspend operator fun invoke(): Result<List<Car>> {
        return repository.getCars()
    }
}