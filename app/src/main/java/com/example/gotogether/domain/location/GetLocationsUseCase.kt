package com.example.gotogether.domain.location

import com.example.gotogether.data.locations.repository.LocationRepository
import com.example.gotogether.data.login.repository.LoginRepository
import com.example.gotogether.domain.login.Login
import javax.inject.Inject

class GetLocationsUseCase@Inject constructor(
    private val repository: LocationRepository
) {
    suspend operator fun invoke(): Result<List<Location>> {
        return repository.getLocations()
    }
}