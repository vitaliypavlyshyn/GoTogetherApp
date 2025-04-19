package com.example.gotogether.domain.trip

data class Trip(
    val tripId: Long?,
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
