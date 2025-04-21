package com.example.gotogether.data.car

import com.example.gotogether.domain.car.Car

data class CarDTO(
    val carId: Long,
    val make: String,
    val model: String
)

fun CarDTO.toDomain(): Car {
    return Car(
        carId = carId,
        make = make,
        model = model
    )
}

fun List<CarDTO>.toDomainList(): List<Car> {
    val cars = mutableListOf<Car>()
    for(carDTO in this) {
        cars.add(carDTO.toDomain())
    }
    return cars
}