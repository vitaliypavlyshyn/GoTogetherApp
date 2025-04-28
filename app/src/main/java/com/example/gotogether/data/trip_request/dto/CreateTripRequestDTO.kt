package com.example.gotogether.data.trip_request.dto

data class CreateTripRequestRequest(
    val tripId: Long,
    val passengerUuid: String,
    val requestedSeats: Int
)

data class ResponseDTO(
    val message: String,
    val isSuccess: Boolean,
)