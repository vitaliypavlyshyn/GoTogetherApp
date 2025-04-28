package com.example.gotogether.data.trip_passenger.repository

import com.example.gotogether.data.trip_passenger.dto.CreateTripPassengerRequest
import com.example.gotogether.data.trip_passenger.dto.CreateTripPassengerResponse
import com.example.gotogether.data.trip_passenger.dto.DeleteTripPassengerResponse
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import com.example.gotogether.domain.trip_passenger.TripPassenger
import com.example.gotogether.domain.user.DeleteUser

interface TripPassengerRepository {
    suspend fun getTripPassengersById(tripId: Long): Result<List<TripPassenger>>
    suspend fun createTripPassenger(request: CreateTripPassengerRequest): Result<ResponseDTO>
    suspend fun deleteTripPassenger(tripId: Long, userUuid: String): Result<ResponseDTO>
}