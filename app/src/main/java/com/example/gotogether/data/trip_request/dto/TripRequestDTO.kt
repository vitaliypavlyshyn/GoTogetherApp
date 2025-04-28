package com.example.gotogether.data.trip_request.dto

import com.example.gotogether.domain.trip_request.TripRequest

data class TripRequestResponse(
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

fun TripRequestResponse.toDomain(): TripRequest {
    return TripRequest(
        requestId = requestId,
        tripId = tripId,
        passengerUuid = passengerUuid,
        passengerFirstName = passengerFirstName,
        passengerLastName = passengerLastName,
        pictureProfile = pictureProfile,
        phoneNumber = phoneNumber,
        requestedSeats = requestedSeats,
        status = status
    )
}

fun List<TripRequestResponse>.toDomainList(): List<TripRequest> {
    val tripRequests = mutableListOf<TripRequest>()
    for(tripRequestDTO in this) {
        tripRequests.add(tripRequestDTO.toDomain())
    }
    return tripRequests
}

