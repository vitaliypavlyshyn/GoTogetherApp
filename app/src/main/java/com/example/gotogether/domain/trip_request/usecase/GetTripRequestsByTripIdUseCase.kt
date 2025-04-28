package com.example.gotogether.domain.trip_request.usecase

import com.example.gotogether.data.trip_request.repository.TripRequestRepository
import com.example.gotogether.domain.trip_request.TripRequest
import javax.inject.Inject

class GetTripRequestsByTripIdUseCase @Inject constructor(
    private val repository: TripRequestRepository
) {
    suspend operator fun invoke(tripId: Long): Result<List<TripRequest>> {
        return repository.getRequestsByTripId(tripId)
    }
}