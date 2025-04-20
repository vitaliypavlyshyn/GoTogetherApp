package com.example.gotogether.domain.trip_passenger

import com.example.gotogether.data.trip_passenger.repository.TripPassengerRepository
import javax.inject.Inject

class GetPassengersByTripIdUseCase @Inject constructor(
    private val repository: TripPassengerRepository
) {
    suspend operator fun invoke(tripId: Long): Result<List<TripPassenger>> {
        return repository.getTripPassengersById(tripId)
    }
}