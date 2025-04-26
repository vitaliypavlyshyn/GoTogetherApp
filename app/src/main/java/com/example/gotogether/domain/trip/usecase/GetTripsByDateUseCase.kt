package com.example.gotogether.domain.trip.usecase

import com.example.gotogether.data.trip.repository.TripRepository
import com.example.gotogether.domain.trip.Trip
import javax.inject.Inject

class GetTripsByDateUseCase @Inject constructor(
    private val repository: TripRepository
) {
    suspend operator fun invoke(fromCityId: Long, toCityId: Long, date: String): Result<List<Trip>> {
        return repository.getAllTripsByDate(fromCityId, toCityId, date)
    }
}