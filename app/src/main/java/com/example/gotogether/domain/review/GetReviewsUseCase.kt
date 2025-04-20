package com.example.gotogether.domain.review

import com.example.gotogether.data.review.repository.ReviewRepository
import javax.inject.Inject


class GetReviewsUseCase @Inject constructor(
    private val repository: ReviewRepository
) {
    suspend operator fun invoke(userUuid: String): Result<List<Review>> {
        return repository.getReviewsByUserUuid(userUuid)
    }
}