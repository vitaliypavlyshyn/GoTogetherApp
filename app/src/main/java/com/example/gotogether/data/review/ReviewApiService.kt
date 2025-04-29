package com.example.gotogether.data.review

import com.example.gotogether.data.trip_request.dto.ResponseDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewApiService {
    @GET("/reviews/reviewed/{reviewedUuid}")
    suspend fun getReviewsByReviewedUuid(@Path("reviewedUuid") reviewedUuid: String): List<ReviewResponse>

    @GET("/reviews/reviewer/{reviewerUuid}")
    suspend fun getReviewsByReviewerUuid(@Path("reviewerUuid") reviewerUuid: String): List<ReviewResponse>

    @GET("/ratings/{userUuid}")
    suspend fun getRatingByUserUuid(@Path("userUuid") userUuid: String): RatingResponse

    @POST("/review")
    suspend fun createReview(@Body createReviewRequest: CreateReviewRequest): ResponseDTO
}