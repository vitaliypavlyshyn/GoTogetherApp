package com.example.gotogether.domain.trip_passenger

data class TripPassenger(
    val tripPassengerId: Long,
    val tripId: Long,
    val passengerUuid: String,
    val firstName: String,
    val lastName: String,
    val pictureProfile: ByteArray?,
    val phoneNumber: String?,
    val seatsBooked: Int,
    val avgRating: Double?,
    val countReviews: Int
)
