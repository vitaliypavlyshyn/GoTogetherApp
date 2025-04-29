package com.example.gotogether.data.review.repository

import com.example.gotogether.data.review.CreateReviewRequest
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import com.example.gotogether.domain.review.Rating
import com.example.gotogether.domain.review.Review

interface ReviewRepository {
    suspend fun getReviewsByReviewedUuid(reviewedUuid: String): Result<List<Review>>
    suspend fun getReviewsByReviewerUuid(reviewerUuid: String): Result<List<Review>>
    suspend fun getRatingByUserUuid(userUuid: String): Result<Rating>
    suspend fun createReview(createReviewRequest: CreateReviewRequest): Result<ResponseDTO>
}