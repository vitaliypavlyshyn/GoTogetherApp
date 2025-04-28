package com.example.gotogether.data.car

import com.example.gotogether.domain.car.Car

data class CarResponse(
    val carId: Long,
    val make: String,
    val model: String
)

fun CarResponse.toDomain(): Car {
    return Car(
        carId = carId,
        make = make,
        model = model
    )
}

fun List<CarResponse>.toDomainList(): List<Car> {
    val cars = mutableListOf<Car>()
    for(carDTO in this) {
        cars.add(carDTO.toDomain())
    }
    return cars
}