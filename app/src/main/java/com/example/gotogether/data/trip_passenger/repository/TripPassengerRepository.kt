package com.example.gotogether.data.trip_passenger.repository

import com.example.gotogether.domain.trip.DetailedTrip
import com.example.gotogether.domain.trip_passenger.TripPassenger

interface TripPassengerRepository {
    suspend fun getTripPassengersById(tripId: Long): Result<List<TripPassenger>>
}