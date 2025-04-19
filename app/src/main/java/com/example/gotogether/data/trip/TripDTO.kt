package com.example.gotogether.data.trip

import com.example.gotogether.domain.trip.Trip

data class TripDTO(
    val tripId: Long?,
    val driverUuid: String,
    val firstName: String,
    val lastName: String,
    val startLocationCity: String,
    val startLocationRegion: String,
    val endLocationCity: String,
    val endLocationRegion: String,
    val startTime: String,
    val endTime: String,
    val distanceInMeters: Int,
    val availableSeats: Int,
    val status: String,
    val price: Int,
    val isFastConfirm: Boolean,
    val avgRating: Double?,
    val avgDrivingSkills: Double?
)

fun TripDTO.toDomain(): Trip {
    return Trip(
        tripId = tripId,
        driverUuid = driverUuid,
        firstName = firstName,
        lastName = lastName,
        startLocationCity = startLocationCity,
        startLocationRegion = startLocationRegion,
        endLocationCity = endLocationCity,
        endLocationRegion = endLocationRegion,
        startTime = startTime,
        endTime = endTime,
        distanceInMeters = distanceInMeters,
        availableSeats = availableSeats,
        status = status,
        price = price,
        isFastConfirm = isFastConfirm,
        avgRating = avgRating,
        avgDrivingSkills = avgDrivingSkills
    )
}

fun List<TripDTO>.toDomainList(): List<Trip> {
    val trips = mutableListOf<Trip>()
    for(tripDTO in this) {
        trips.add(tripDTO.toDomain())
    }
    return trips
}