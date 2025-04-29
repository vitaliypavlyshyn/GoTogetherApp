package com.example.gotogether.domain.review

import com.example.gotogether.data.review.repository.ReviewRepository
import javax.inject.Inject


class GetReviewsByReviewedUuidUseCase @Inject constructor(
    private val repository: ReviewRepository
) {
    suspend operator fun invoke(reviewedUuid: String): Result<List<Review>> {
        return repository.getReviewsByReviewedUuid(reviewedUuid)
    }
}