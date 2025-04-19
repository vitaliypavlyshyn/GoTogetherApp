package com.example.gotogether.data.trip

import retrofit2.http.GET
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
}