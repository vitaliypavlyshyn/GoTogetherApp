package com.example.gotogether.data.trip.dto

data class UpdateTripRequest(
    val status: String? = null,
    val availableSeats: Int? = null
)
