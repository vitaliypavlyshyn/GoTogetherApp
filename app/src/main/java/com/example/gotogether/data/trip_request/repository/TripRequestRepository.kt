package com.example.gotogether.data.trip_request.repository

import com.example.gotogether.data.trip_request.dto.CreateTripRequestRequest
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import com.example.gotogether.data.trip_request.dto.UpdateTripRequestRequest
import com.example.gotogether.domain.trip_request.TripRequest

interface TripRequestRepository {
    suspend fun getRequestsByTripId(tripId: Long): Result<List<TripRequest>>
    suspend fun getRequestsByUserUuid(userUuid: String): Result<List<TripRequest>>
    suspend fun getRequestByTripIdAndUserUuid(tripId: Long, userUuid: String): Result<TripRequest>
    suspend fun createTripRequest(request: CreateTripRequestRequest): Result<ResponseDTO>
    suspend fun updateTripRequest(requestId: Long, updateTripRequestRequest: UpdateTripRequestRequest): Result<ResponseDTO>
    suspend fun deleteTripRequest(requestId: Long): Result<ResponseDTO>
}