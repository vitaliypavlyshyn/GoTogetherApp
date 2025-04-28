package com.example.gotogether.domain.trip_request.usecase

import com.example.gotogether.data.trip_request.dto.ResponseDTO
import com.example.gotogether.data.trip_request.repository.TripRequestRepository
import javax.inject.Inject

class DeleteTripRequestUseCase @Inject constructor(
    private val repository: TripRequestRepository
) {
    suspend operator fun invoke(requestId: Long): Result<ResponseDTO> {
        return repository.deleteTripRequest(requestId)
    }
}