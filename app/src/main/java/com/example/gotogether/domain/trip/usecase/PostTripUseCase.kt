package com.example.gotogether.domain.trip.usecase

import com.example.gotogether.data.trip.dto.CreateTripRequest
import com.example.gotogether.data.trip.dto.CreateTripResponse
import com.example.gotogether.data.trip.repository.TripRepository
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import com.example.gotogether.domain.trip.CreateTrip
import javax.inject.Inject

class PostTripUseCase @Inject constructor(
    private val repository: TripRepository
) {
    suspend operator fun invoke(request: CreateTripRequest): Result<ResponseDTO> {
        return repository.createTrip(request)
    }
}