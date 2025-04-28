package com.example.gotogether.data.trip_request.repository

import com.example.gotogether.data.trip_request.TripRequestApiService
import com.example.gotogether.data.trip_request.dto.CreateTripRequestRequest
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import com.example.gotogether.data.trip_request.dto.UpdateTripRequestRequest
import com.example.gotogether.data.trip_request.dto.toDomain
import com.example.gotogether.data.trip_request.dto.toDomainList
import com.example.gotogether.domain.trip_request.TripRequest
import javax.inject.Inject

class TripRequestRepositoryImpl @Inject constructor(
    private val api: TripRequestApiService
): TripRequestRepository {
    override suspend fun getRequestsByTripId(tripId: Long): Result<List<TripRequest>> {
        return try {
            val response = api.getRequestsByTripId(tripId)
            Result.success(response.toDomainList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getRequestsByUserUuid(userUuid: String): Result<List<TripRequest>> {
        return try {
            val response = api.getRequestsByUserUuid(userUuid)
            Result.success(response.toDomainList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getRequestByTripIdAndUserUuid(
        tripId: Long,
        userUuid: String,
    ): Result<TripRequest> {
        return try {
            val response = api.getRequestByTripIdAndUserUuid(tripId, userUuid)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createTripRequest(request: CreateTripRequestRequest): Result<ResponseDTO> {
        return try {
            val response = api.createTripRequest(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateTripRequest(requestId: Long, updateTripRequestRequest: UpdateTripRequestRequest): Result<ResponseDTO> {
        return try {
            val response = api.updateTripRequest(requestId, updateTripRequestRequest)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteTripRequest(requestId: Long): Result<ResponseDTO> {
        return try {
            val response = api.deleteTripRequest(requestId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}