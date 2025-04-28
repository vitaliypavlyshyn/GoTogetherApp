package com.example.gotogether.data.activity_log

import com.example.gotogether.data.trip_request.dto.ResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ActivityLogApiService {
    @POST("/activity")
    suspend fun saveActivity(@Body request: ActivityLogRequest): ResponseDTO

    @GET("/activity/{userUuid}")
    suspend fun getActivitiesByUserUuid(@Path("userUuid") userUuid: String) : List<ActivityLogResponse>

}