package com.example.gotogether.data.review.repository

import com.example.gotogether.domain.review.Rating
import com.example.gotogether.domain.review.Review

interface ReviewRepository {
    suspend fun getReviewsByUserUuid(userUuid: String): Result<List<Review>>
    suspend fun getRatingByUserUuid(userUuid: String): Result<Rating>
}