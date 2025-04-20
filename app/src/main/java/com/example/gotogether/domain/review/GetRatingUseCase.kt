package com.example.gotogether.domain.review

import com.example.gotogether.data.review.repository.ReviewRepository
import javax.inject.Inject

class GetRatingUseCase @Inject constructor(
    private val repository: ReviewRepository
) {
    suspend operator fun invoke(userUuid: String): Result<Rating> {
        return repository.getRatingByUserUuid(userUuid)
    }
}