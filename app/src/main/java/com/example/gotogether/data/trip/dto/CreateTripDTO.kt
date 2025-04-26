package com.example.gotogether.data.trip.dto

import com.example.gotogether.data.registration.RegistrationResponseDTO
import com.example.gotogether.domain.registration.Registration
import com.example.gotogether.domain.trip.CreateTrip

data class CreateTripRequestDTO (
    val driverUuid: String,
    val startLocationId: Long,
    val endLocationId: Long,
    val startTime: String,
    val endTime: String,
    val distanceInMeters: Int,
    val availableSeats: Int,
    val price: Int,
    val isFastConfirm: Boolean
)

data class CreateTripResponseDTO (
    val message: String,
    val isSuccess: Boolean
)

fun CreateTripResponseDTO.toDomain(): CreateTrip {
    return CreateTrip(
        isSuccess = isSuccess,
        message = message
    )
}