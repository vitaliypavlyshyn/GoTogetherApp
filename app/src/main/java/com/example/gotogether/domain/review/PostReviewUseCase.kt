package com.example.gotogether.domain.review

import com.example.gotogether.data.review.CreateReviewRequest
import com.example.gotogether.data.review.repository.ReviewRepository
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import javax.inject.Inject

class PostReviewUseCase @Inject constructor(
    private val repository: ReviewRepository
) {
    suspend operator fun invoke(request: CreateReviewRequest): Result<ResponseDTO> {
        return repository.createReview(request)
    }
}