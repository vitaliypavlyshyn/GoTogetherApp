package com.example.gotogether.data.trip.repository

import com.example.gotogether.domain.trip.Trip

interface TripRepository {
    suspend fun getAllTripsByDate(fromCityId: Long, toCityId: Long, date: String): Result<List<Trip>>
}