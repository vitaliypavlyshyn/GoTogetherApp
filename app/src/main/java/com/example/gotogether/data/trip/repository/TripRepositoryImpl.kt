package com.example.gotogether.data.trip.repository

import com.example.gotogether.data.trip.TripApiService
import com.example.gotogether.data.trip.dto.CreateTripRequest
import com.example.gotogether.data.trip.dto.CreateTripResponse
import com.example.gotogether.data.trip.dto.UpdateTripRequest
import com.example.gotogether.data.trip.dto.toDomain
import com.example.gotogether.data.trip.dto.toDomainList
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import com.example.gotogether.domain.trip.DetailedTrip
import com.example.gotogether.domain.trip.Trip
import retrofit2.Retrofit
import javax.inject.Inject

class TripRepositoryImpl @Inject constructor(
    private val api: TripApiService,
    private val retrofit: Retrofit
) : TripRepository{
    override suspend fun getAllTripsByDate(fromCityId: Long, toCityId: Long, date: String): Result<List<Trip>> {
        return try {
            val trips = api.getTripsByDate(fromCityId, toCityId, date)
            Result.success(trips.toDomainList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTripsByDriverUuid(driverUuid: String): Result<List<Trip>> {
        return try {
            val trips = api.getTripsByDriverUuid(driverUuid)
            Result.success(trips.toDomainList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTripsByPassengerUuid(passengerUuid: String): Result<List<Trip>> {
        return try {
            val trips = api.getTripsByPassengerUuid(passengerUuid)
            Result.success(trips.toDomainList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTripsByRequesterUuid(requester: String): Result<List<Trip>> {
        return try {
            val trips = api.getTripsByRequesterUuid(requester)
            Result.success(trips.toDomainList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDetailedTripById(tripId: Long): Result<DetailedTrip> {
        return try {
            val trip = api.getDetailedTripById(tripId)
            Result.success(trip.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createTrip(request: CreateTripRequest): Result<ResponseDTO> {
        return try {
            val response = api.createTrip(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteTrip(tripId: Long): Result<ResponseDTO> {
        return try {
            val response = api.deleteTrip(tripId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateTrip(
        tripId: Long,
        request: UpdateTripRequest,
    ): Result<ResponseDTO> {
        return try {
            val response = api.updateTrip(tripId, request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}