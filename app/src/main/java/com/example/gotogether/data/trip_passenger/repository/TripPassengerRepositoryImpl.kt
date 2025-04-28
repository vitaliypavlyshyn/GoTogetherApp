package com.example.gotogether.data.trip_passenger.repository

import com.example.gotogether.data.trip_passenger.TripPassengerApiService
import com.example.gotogether.data.trip_passenger.dto.CreateTripPassengerRequest
import com.example.gotogether.data.trip_passenger.dto.toDomainList
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import com.example.gotogether.domain.trip_passenger.TripPassenger
import retrofit2.Retrofit
import javax.inject.Inject

class TripPassengerRepositoryImpl @Inject constructor(
    private val api: TripPassengerApiService,
    private val retrofit: Retrofit
): TripPassengerRepository {
    override suspend fun getTripPassengersById(tripId: Long): Result<List<TripPassenger>> {
        return try {
            val passengers = api.getTripPassengersByTripId(tripId)
            Result.success(passengers.toDomainList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createTripPassenger(request: CreateTripPassengerRequest): Result<ResponseDTO>  {
        return try {
            val response = api.saveTripPassenger(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteTripPassenger(
        tripId: Long,
        userUuid: String,
    ): Result<ResponseDTO> {
        return try {
            val response = api.deleteTripPassenger(tripId, userUuid)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}