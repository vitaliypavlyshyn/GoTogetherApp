package com.example.gotogether.data.trip.dto

data class TripExportDTO(
    val tripId: Long,
    val startTime: String,
    val endTime: String,
    val startLocationCity: String,
    val startLocationRegion: String,
    val endLocationCity: String,
    val endLocationRegion: String,
    val price: Int,
    val status: String,
    val driverFirstName: String,
    val avgRating: Double?
)