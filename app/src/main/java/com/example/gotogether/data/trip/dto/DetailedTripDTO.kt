package com.example.gotogether.data.trip.dto

import com.example.gotogether.domain.trip.DetailedTrip
import com.example.gotogether.utils.mapper.TimeMapper

data class DetailedTripDTO(
    val tripId: Long?,
    val driverUuid: String,
    val driverFirstName: String,
    val driverLastName: String,
    val driverPicture: ByteArray?,
    val driverPhoneNumber: String?,
    val description: String?,
    val distanceInMeters: Int,
    val availableSeats: Int,
    val status: String,
    val price: Int,
    val isFastConfirm: Boolean,
    val carMake: String,
    val carModel: String,
    val startCity: String,
    val startRegion: String,
    val startLat: String,
    val startLng: String,
    val endCity: String,
    val endRegion: String,
    val endLat: String,
    val endLng: String,
    val startTime: String,
    val endTime: String,
    val avgRating: Double?,
    val avgDrivingSkills: Double?,
    val countReviews: Int
)

fun DetailedTripDTO.toDomain(): DetailedTrip {
    val startKyivTime = TimeMapper.toDateWithTimeZone(startTime)
    val endKyivTime = TimeMapper.toDateWithTimeZone(endTime)
    return DetailedTrip(
        tripId = tripId,
        driverUuid = driverUuid,
        driverFirstName = driverFirstName,
        driverLastName = driverLastName,
        driverPicture = driverPicture,
        driverPhoneNumber = driverPhoneNumber,
        description = description,
        distanceInMeters = distanceInMeters,
        availableSeats = availableSeats,
        status = status,
        price = price,
        isFastConfirm = isFastConfirm,
        carMake = carMake,
        carModel = carModel,
        startCity = startCity,
        startRegion = startRegion,
        startLat = startLat,
        startLng = startLng,
        endCity = endCity,
        endRegion = endRegion,
        endLat = endLat,
        endLng = endLng,
        startTime = startKyivTime.toString(),
        endTime = endKyivTime.toString(),
        avgRating = avgRating,
        avgDrivingSkills = avgDrivingSkills,
        countReviews = countReviews
    )
}