package com.example.gotogether.data.location.repository

import com.example.gotogether.data.location.LocationApiService
import com.example.gotogether.data.location.toDomainList
import com.example.gotogether.domain.location.Location
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val api: LocationApiService
) : LocationRepository{
    override suspend fun getLocations(): Result<List<Location>> {
        return try {
            val locations = api.getLocations()
            Result.success(locations.toDomainList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}