package com.example.gotogether.data.trip.repository

import com.example.gotogether.data.trip.dto.CreateTripRequest
import com.example.gotogether.data.trip.dto.UpdateTripRequest
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import com.example.gotogether.domain.trip.DetailedTrip
import com.example.gotogether.domain.trip.Trip

interface TripRepository {
    suspend fun getAllTripsByDate(fromCityId: Long, toCityId: Long, date: String): Result<List<Trip>>
    suspend fun getTripsByDriverUuid(driverUuid: String): Result<List<Trip>>
    suspend fun getTripsByPassengerUuid(passengerUuid: String): Result<List<Trip>>
    suspend fun getTripsByRequesterUuid(requester: String): Result<List<Trip>>
    suspend fun getDetailedTripById(tripId: Long): Result<DetailedTrip>
    suspend fun createTrip(request: CreateTripRequest): Result<ResponseDTO>
    suspend fun deleteTrip(tripId: Long): Result<ResponseDTO>
    suspend fun updateTrip(tripId: Long, request: UpdateTripRequest): Result<ResponseDTO>
}