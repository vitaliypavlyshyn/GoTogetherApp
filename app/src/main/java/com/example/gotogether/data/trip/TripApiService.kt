package com.example.gotogether.data.trip

import com.example.gotogether.data.trip.dto.CreateTripRequest
import com.example.gotogether.data.trip.dto.CreateTripResponse
import com.example.gotogether.data.trip.dto.DetailedTripDTO
import com.example.gotogether.data.trip.dto.TripDTO
import com.example.gotogether.data.trip.dto.UpdateTripRequest
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import com.example.gotogether.data.user.UpdateUserRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TripApiService {
    @GET("/trips")
    suspend fun getTripsByDate(
        @Query("fromCityId") fromCityId: Long,
        @Query("toCityId") toCityId: Long,
        @Query("date") date: String
    ): List<TripDTO>

    @GET("/trips/driver/{driverUuid}")
    suspend fun getTripsByDriverUuid(@Path("driverUuid") driverUuid: String): List<TripDTO>

    @GET("/trips/passenger/{passengerUuid}")
    suspend fun getTripsByPassengerUuid(@Path("passengerUuid") passengerUuid: String): List<TripDTO>

    @GET("/trips/requester/{requesterUuid}")
    suspend fun getTripsByRequesterUuid(@Path("requesterUuid") requesterUuid: String): List<TripDTO>

    @GET("/trips/{tripId}")
    suspend fun getDetailedTripById(@Path("tripId") tripId: Long): DetailedTripDTO

    @POST("/trips")
    suspend fun createTrip(@Body request: CreateTripRequest): ResponseDTO

    @DELETE("/trips/{tripId}")
    suspend fun deleteTrip(@Path("tripId") tripId: Long): ResponseDTO

    @PUT("/trips/{tripId}")
    suspend fun updateTrip(
        @Path("tripId") tripId: Long,
        @Body request: UpdateTripRequest
    ): ResponseDTO
}