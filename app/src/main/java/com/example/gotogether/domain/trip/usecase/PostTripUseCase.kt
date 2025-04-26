package com.example.gotogether.domain.trip.usecase

import com.example.gotogether.data.trip.dto.CreateTripRequestDTO
import com.example.gotogether.data.trip.repository.TripRepository
import com.example.gotogether.domain.trip.CreateTrip
import javax.inject.Inject

class PostTripUseCase @Inject constructor(
    private val repository: TripRepository
) {
    suspend operator fun invoke(request: CreateTripRequestDTO): Result<CreateTrip> {
        return repository.createTrip(request)
    }
}