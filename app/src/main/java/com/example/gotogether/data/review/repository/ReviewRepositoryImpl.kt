package com.example.gotogether.data.review.repository

import com.example.gotogether.data.review.CreateReviewRequest
import com.example.gotogether.data.review.ReviewApiService
import com.example.gotogether.data.review.toDomain
import com.example.gotogether.data.review.toDomainList
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import com.example.gotogether.domain.review.Rating
import com.example.gotogether.domain.review.Review
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val api: ReviewApiService
): ReviewRepository {
    override suspend fun getReviewsByReviewedUuid(reviewedUuid: String): Result<List<Review>> {
        return try {
            val reviews= api.getReviewsByReviewedUuid(reviewedUuid)
            Result.success(reviews.toDomainList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getReviewsByReviewerUuid(reviewerUuid: String): Result<List<Review>> {
        return try {
            val reviews= api.getReviewsByReviewerUuid(reviewerUuid)
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

    override suspend fun createReview(createReviewRequest: CreateReviewRequest): Result<ResponseDTO> {
        return try {
            val response = api.createReview(createReviewRequest)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}