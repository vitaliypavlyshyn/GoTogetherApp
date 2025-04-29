package com.example.gotogether.domain.review

import com.example.gotogether.data.review.repository.ReviewRepository
import javax.inject.Inject


class GetReviewsByReviewerUuidUseCase @Inject constructor(
    private val repository: ReviewRepository
) {
    suspend operator fun invoke(reviewerUuid: String): Result<List<Review>> {
        return repository.getReviewsByReviewerUuid(reviewerUuid)
    }
}