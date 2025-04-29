package com.example.gotogether.domain.trip

import com.example.gotogether.data.trip.dto.TripExportDTO

data class Trip(
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

fun Trip.toTripExportDTO(): TripExportDTO{
    return TripExportDTO(
        tripId = tripId,
        startTime = startTime,
        endTime = endTime,
        startLocationCity = startLocationCity,
        startLocationRegion = startLocationRegion,
        endLocationCity = endLocationCity,
        endLocationRegion = endLocationRegion,
        price = price,
        status = status,
        driverFirstName = driverFirstName,
        avgRating = avgRating
    )
}

fun List<Trip>.toListTripExportDTO(): List<TripExportDTO> {
    val listDTO = mutableListOf<TripExportDTO>()
    for(i in this) {
        listDTO.add(i.toTripExportDTO())
    }
    return listDTO
}