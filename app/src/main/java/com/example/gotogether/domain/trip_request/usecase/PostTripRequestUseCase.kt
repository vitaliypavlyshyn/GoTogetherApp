package com.example.gotogether.domain.trip_request.usecase

import com.example.gotogether.data.trip_request.dto.CreateTripRequestRequest
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import com.example.gotogether.data.trip_request.repository.TripRequestRepository
import javax.inject.Inject

class PostTripRequestUseCase @Inject constructor(
    private val repository: TripRequestRepository
) {
    suspend operator fun invoke(request: CreateTripRequestRequest): Result<ResponseDTO> {
        return repository.createTripRequest(request)
    }
}