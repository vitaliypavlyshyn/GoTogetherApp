package com.example.gotogether.domain.trip_passenger.usecase

import com.example.gotogether.data.trip_passenger.dto.DeleteTripPassengerResponse
import com.example.gotogether.data.trip_passenger.repository.TripPassengerRepository
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import javax.inject.Inject

class DeletePassengerUseCase @Inject constructor(
    private val repository: TripPassengerRepository
) {
    suspend operator fun invoke(tripId: Long, userUuid: String): Result<ResponseDTO> {
        return repository.deleteTripPassenger(tripId, userUuid)
    }
}