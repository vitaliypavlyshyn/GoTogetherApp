package com.example.gotogether.data.trip.repository

import com.example.gotogether.data.trip.dto.CreateTripRequestDTO
import com.example.gotogether.domain.trip.CreateTrip
import com.example.gotogether.domain.trip.DetailedTrip
import com.example.gotogether.domain.trip.Trip

interface TripRepository {
    suspend fun getAllTripsByDate(fromCityId: Long, toCityId: Long, date: String): Result<List<Trip>>
    suspend fun getDetailedTripById(tripId: Long): Result<DetailedTrip>
    suspend fun createTrip(request: CreateTripRequestDTO): Result<CreateTrip>
}