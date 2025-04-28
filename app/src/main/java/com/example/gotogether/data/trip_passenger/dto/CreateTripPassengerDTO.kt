package com.example.gotogether.data.trip_passenger.dto

data class CreateTripPassengerRequest(
    val tripId: Long,
    val passengerUuid: String,
    val seatsBooked: Int
)


data class CreateTripPassengerResponse(
    val message: String,
    val isSuccess: Boolean,
)