package com.example.gotogether.data.review

import retrofit2.http.GET
import retrofit2.http.Path

interface ReviewApiService {
    @GET("/reviews/{userUuid}")
    suspend fun getReviewsByUserUuid(@Path("userUuid") userUuid: String): List<ReviewDTO>

    @GET("/ratings/{userUuid}")
    suspend fun getRatingByUserUuid(@Path("userUuid") userUuid: String): RatingDTO
}