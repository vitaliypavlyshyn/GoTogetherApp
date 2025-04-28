package com.example.gotogether.domain.trip_passenger.usecase

import com.example.gotogether.data.trip_passenger.dto.CreateTripPassengerRequest
import com.example.gotogether.data.trip_passenger.dto.CreateTripPassengerResponse
import com.example.gotogether.data.trip_passenger.repository.TripPassengerRepository
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import javax.inject.Inject

class PostPassengerUseCase @Inject constructor(
    private val repository: TripPassengerRepository
) {
    suspend operator fun invoke(request: CreateTripPassengerRequest): Result<ResponseDTO> {
        return repository.createTripPassenger(request)
    }
}