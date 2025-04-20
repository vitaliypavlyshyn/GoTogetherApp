package com.example.gotogether.domain.review

data class Review(
    val reviewId: Long,
    val tripId: Long,
    val reviewerUuid: String,
    val reviewerFirstName: String,
    val reviewerPicture: ByteArray?,
    val reviewedUserUuid: String,
    val rating: Int?,
    val drivingSkills: Int?,
    val comment: String,
    val createdAt: String
)
