package com.example.gotogether.domain.location

import com.example.gotogether.data.location.repository.LocationRepository
import javax.inject.Inject

class GetLocationsUseCase@Inject constructor(
    private val repository: LocationRepository
) {
    suspend operator fun invoke(): Result<List<Location>> {
        return repository.getLocations()
    }
}