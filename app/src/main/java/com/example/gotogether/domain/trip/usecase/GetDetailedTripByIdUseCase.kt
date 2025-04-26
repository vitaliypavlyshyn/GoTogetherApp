package com.example.gotogether.domain.trip.usecase

import com.example.gotogether.data.trip.repository.TripRepository
import com.example.gotogether.domain.trip.DetailedTrip
import javax.inject.Inject

class GetDetailedTripByIdUseCase @Inject constructor(
    private val repository: TripRepository
) {
    suspend operator fun invoke(tripId: Long): Result<DetailedTrip> {
        return repository.getDetailedTripById(tripId)
    }
}