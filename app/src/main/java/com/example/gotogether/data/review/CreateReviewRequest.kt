package com.example.gotogether.data.review

data class CreateReviewRequest(
    val tripId: Long,
    val reviewerUuid: String,
    val reviewedUserUuid: String,
    val rating: Int,
    val drivingSkills: Int? = null,
    val comment: String,
)
