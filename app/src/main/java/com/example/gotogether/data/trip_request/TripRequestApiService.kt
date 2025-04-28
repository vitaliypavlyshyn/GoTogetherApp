package com.example.gotogether.data.trip_request

import com.example.gotogether.data.trip_request.dto.CreateTripRequestRequest
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import com.example.gotogether.data.trip_request.dto.TripRequestResponse
import com.example.gotogether.data.trip_request.dto.UpdateTripRequestRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TripRequestApiService {
    @GET("/requests/{tripId}")
    suspend fun getRequestsByTripId(@Path("tripId") tripId: Long): List<TripRequestResponse>

    @GET("/requests/user/{userUuid}")
    suspend fun getRequestsByUserUuid(@Path("userUuid") userUuid: String): List<TripRequestResponse>

    @GET("/request/{tripId}/{userUuid}")
    suspend fun getRequestByTripIdAndUserUuid(@Path("tripId") tripId: Long, @Path("userUuid") userUuid: String): TripRequestResponse

    @PUT("/request/{requestId}")
    suspend fun updateTripRequest(@Path("requestId") requestId: Long, @Body request: UpdateTripRequestRequest): ResponseDTO

    @DELETE("request/{requestId}")
    suspend fun deleteTripRequest(@Path("requestId") requestId: Long): ResponseDTO

    @POST("/request")
    suspend fun createTripRequest(@Body request: CreateTripRequestRequest): ResponseDTO
}