package com.example.gotogether.domain.trip.usecase

import com.example.gotogether.data.trip.repository.TripRepository
import com.example.gotogether.domain.trip.Trip
import javax.inject.Inject


class GetTripsByRequesterUuidUseCase @Inject constructor(
    private val repository: TripRepository
) {
    suspend operator fun invoke(requesterUuid: String): Result<List<Trip>> {
        return repository.getTripsByRequesterUuid(requesterUuid)
    }
}