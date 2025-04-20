package com.example.gotogether.data.trip_passenger.repository

import com.example.gotogether.data.trip.toDomain
import com.example.gotogether.data.trip_passenger.TripPassengerApiService
import com.example.gotogether.data.trip_passenger.toDomainList
import com.example.gotogether.domain.trip_passenger.TripPassenger
import javax.inject.Inject

class TripPassengerRepositoryImpl @Inject constructor(
    private val api: TripPassengerApiService
): TripPassengerRepository {
    override suspend fun getTripPassengersById(tripId: Long): Result<List<TripPassenger>> {
        return try {
            val passengers = api.getTripPassengersByTripId(tripId)
            Result.success(passengers.toDomainList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}