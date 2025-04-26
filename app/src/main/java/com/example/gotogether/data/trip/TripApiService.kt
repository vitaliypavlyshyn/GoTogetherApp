package com.example.gotogether.data.trip

import com.example.gotogether.data.trip.dto.CreateTripRequestDTO
import com.example.gotogether.data.trip.dto.CreateTripResponseDTO
import com.example.gotogether.data.trip.dto.DetailedTripDTO
import com.example.gotogether.data.trip.dto.TripDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TripApiService {
    @GET("/trips")
    suspend fun getTripsByDate(
        @Query("fromCityId") fromCityId: Long,
        @Query("toCityId") toCityId: Long,
        @Query("date") date: String
    ): List<TripDTO>

    @GET("/trips/{tripId}")
    suspend fun getDetailedTripById(@Path("tripId") tripId: Long): DetailedTripDTO

    @POST("/trips")
    suspend fun createTrip(@Body request: CreateTripRequestDTO): CreateTripResponseDTO
}