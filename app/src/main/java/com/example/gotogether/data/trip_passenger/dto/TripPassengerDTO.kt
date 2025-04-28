package com.example.gotogether.data.trip_passenger.dto

import com.example.gotogether.domain.trip_passenger.TripPassenger

data class TripPassengerResponse(
    val tripPassengerId: Long,
    val tripId: Long,
    val passengerUuid: String,
    val firstName: String,
    val lastName: String,
    val pictureProfile: ByteArray?,
    val phoneNumber: String?,
    val seatsBooked: Int,
    val avgRating: Double?,
    val countReviews: Int,
)

fun TripPassengerResponse.toDomain(): TripPassenger {
    return TripPassenger(
        tripPassengerId = tripPassengerId,
        tripId = tripId,
        passengerUuid = passengerUuid,
        firstName = firstName,
        lastName = lastName,
        pictureProfile = pictureProfile,
        seatsBooked = seatsBooked,
        avgRating = avgRating,
        countReviews = countReviews,
        phoneNumber = phoneNumber

    )
}

fun List<TripPassengerResponse>.toDomainList(): List<TripPassenger> {
    val passengers = mutableListOf<TripPassenger>()
    for(passengerDTO in this) {
        passengers.add(passengerDTO.toDomain())
    }
    return passengers
}