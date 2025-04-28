package com.example.gotogether.domain.trip_request.usecase

import com.example.gotogether.data.trip_request.repository.TripRequestRepository
import com.example.gotogether.domain.trip_request.TripRequest
import javax.inject.Inject

class GetTripRequestsByUserUuidUseCase @Inject constructor(
    private val repository: TripRequestRepository
) {
    suspend operator fun invoke(userUuid: String): Result<List<TripRequest>> {
        return repository.getRequestsByUserUuid(userUuid)
    }
}