package com.example.gotogether.data.trip.repository

import com.example.gotogether.data.location.toDomainList
import com.example.gotogether.data.registration.RegistrationResponseDTO
import com.example.gotogether.data.registration.toDomain
import com.example.gotogether.data.trip.TripApiService
import com.example.gotogether.data.trip.dto.CreateTripRequestDTO
import com.example.gotogether.data.trip.dto.CreateTripResponseDTO
import com.example.gotogether.data.trip.dto.toDomain
import com.example.gotogether.data.trip.dto.toDomainList
import com.example.gotogether.domain.trip.CreateTrip
import com.example.gotogether.domain.trip.DetailedTrip
import com.example.gotogether.domain.trip.Trip
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Inject

class TripRepositoryImpl @Inject constructor(
    private val api: TripApiService,
    private val retrofit: Retrofit
) : TripRepository{
    override suspend fun getAllTripsByDate(fromCityId: Long, toCityId: Long, date: String): Result<List<Trip>> {
        return try {
            val locations = api.getTripsByDate(fromCityId, toCityId, date)
            Result.success(locations.toDomainList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDetailedTripById(tripId: Long): Result<DetailedTrip> {
        return try {
            val trip = api.getDetailedTripById(tripId)
            Result.success(trip.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createTrip(request: CreateTripRequestDTO): Result<CreateTrip> {
        return try {
            val response = api.createTrip(request)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}