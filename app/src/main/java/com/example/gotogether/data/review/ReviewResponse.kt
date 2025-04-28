package com.example.gotogether.data.review

import com.example.gotogether.domain.review.Review

data class ReviewResponse(
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

fun ReviewResponse.toDomain(): Review {
    return Review(
        reviewId = reviewId,
        tripId = tripId,
        reviewerUuid = reviewerUuid,
        reviewerFirstName = reviewerFirstName,
        reviewerPicture = reviewerPicture,
        reviewedUserUuid = reviewedUserUuid,
        rating = rating,
        drivingSkills = drivingSkills,
        comment = comment,
        createdAt = createdAt
    )
}

fun List<ReviewResponse>.toDomainList(): List<Review> {
    val reviews = mutableListOf<Review>()
    for(review in this) {
        reviews.add(review.toDomain())
    }
    return reviews
}