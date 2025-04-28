package com.example.gotogether.data.trip_passenger

import com.example.gotogether.data.trip_passenger.dto.CreateTripPassengerRequest
import com.example.gotogether.data.trip_passenger.dto.CreateTripPassengerResponse
import com.example.gotogether.data.trip_passenger.dto.DeleteTripPassengerResponse
import com.example.gotogether.data.trip_passenger.dto.TripPassengerResponse
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TripPassengerApiService {

    @GET("/passengers/{tripId}")
    suspend fun getTripPassengersByTripId(@Path("tripId") tripId: Long): List<TripPassengerResponse>

    @POST("/passenger")
    suspend fun saveTripPassenger(@Body request: CreateTripPassengerRequest): ResponseDTO

    @POST("/passengers/{tripId}/{passengerUuid}")
    suspend fun deleteTripPassenger(@Path("tripId") tripId: Long, @Path("passengerUuid") passengerUuid: String): ResponseDTO
}