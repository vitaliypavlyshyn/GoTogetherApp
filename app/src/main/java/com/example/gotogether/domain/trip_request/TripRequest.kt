package com.example.gotogether.domain.trip_request

data class TripRequest(
    val requestId: Long,
    val tripId: Long,
    val passengerUuid: String,
    val passengerFirstName: String?,
    val passengerLastName: String?,
    val pictureProfile: ByteArray?,
    val phoneNumber: String?,
    val requestedSeats: Int,
    val status: String,
)
