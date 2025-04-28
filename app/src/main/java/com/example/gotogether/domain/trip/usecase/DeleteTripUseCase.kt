package com.example.gotogether.domain.trip.usecase

import com.example.gotogether.data.trip.repository.TripRepository
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import javax.inject.Inject

class DeleteTripUseCase @Inject constructor(
    private val repository: TripRepository
) {
    suspend operator fun invoke(tripId: Long): Result<ResponseDTO> {
        return repository.deleteTrip(tripId)
    }
}