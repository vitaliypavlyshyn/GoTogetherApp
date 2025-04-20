package com.example.gotogether.data.trip.repository

import com.example.gotogether.data.trip.TripApiService
import com.example.gotogether.data.trip.toDomain
import com.example.gotogether.data.trip.toDomainList
import com.example.gotogether.domain.trip.DetailedTrip
import com.example.gotogether.domain.trip.Trip
import javax.inject.Inject

class TripRepositoryImpl @Inject constructor(
    private val api: TripApiService
) : TripRepository{
    override suspend fun getAllTripsByDate(fromCityId: Long, toCityId: Long, date: String): Result<List<Trip>> {
        return try {
            val locations = api.getTripsByDate(fromCityId, toCityId, date)
            Result.success(locations.toDomainList())
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
}