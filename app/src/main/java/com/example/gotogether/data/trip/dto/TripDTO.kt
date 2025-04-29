package com.example.gotogether.data.trip.dto

import com.example.gotogether.domain.trip.Trip
import com.example.gotogether.utils.mapper.TimeMapper

data class TripDTO(
    val tripId: Long,
    val driverUuid: String,
    val driverFirstName: String,
    val driverLastName: String,
    val driverPicture: ByteArray?,
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
    val startKyivTime = TimeMapper.toDateWithTimeZone(startTime)
    val endKyivTime = TimeMapper.toDateWithTimeZone(endTime)

    return Trip(
        tripId = tripId,
        driverUuid = driverUuid,
        driverFirstName = driverFirstName,
        driverLastName = driverLastName,
        driverPicture = driverPicture,
        startLocationCity = startLocationCity,
        startLocationRegion = startLocationRegion,
        endLocationCity = endLocationCity,
        endLocationRegion = endLocationRegion,
        startTime = startKyivTime.toString(),
        endTime = endKyivTime.toString(),
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