package com.example.gotogether.domain.trip.usecase

import com.example.gotogether.data.trip.repository.TripRepository
import com.example.gotogether.domain.trip.Trip
import javax.inject.Inject

class GetTripsByDriverUuid @Inject constructor(
    private val repository: TripRepository
) {
    suspend operator fun invoke(driverUuid: String): Result<List<Trip>> {
        return repository.getTripsByDriverUuid(driverUuid)
    }
}