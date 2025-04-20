package com.example.gotogether.data.trip_passenger

import retrofit2.http.GET
import retrofit2.http.Path

interface TripPassengerApiService {

    @GET("/passengers/{tripId}")
    suspend fun getTripPassengersByTripId(@Path("tripId") tripId: Long): List<TripPassengerDTO>
}