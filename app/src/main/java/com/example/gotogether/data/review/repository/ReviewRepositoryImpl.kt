package com.example.gotogether.data.review.repository

import com.example.gotogether.data.review.ReviewApiService
import com.example.gotogether.data.review.toDomain
import com.example.gotogether.data.review.toDomainList
import com.example.gotogether.domain.review.Rating
import com.example.gotogether.domain.review.Review
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val api: ReviewApiService
): ReviewRepository {
    override suspend fun getReviewsByUserUuid(userUuid: String): Result<List<Review>> {
        return try {
            val reviews= api.getReviewsByUserUuid(userUuid)
            Result.success(reviews.toDomainList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getRatingByUserUuid(userUuid: String): Result<Rating> {
        return try {
            val rating = api.getRatingByUserUuid(userUuid)
            Result.success(rating.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}