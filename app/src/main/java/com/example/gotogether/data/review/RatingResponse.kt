package com.example.gotogether.data.review

import com.example.gotogether.domain.review.Rating

data class RatingResponse(
    val avgRating: Double?,
    val avgDrivingSkills: Double?,
    val countReviews: Int
)

fun RatingResponse.toDomain(): Rating {
    return Rating(
        avgRating = avgRating,
        avgDrivingSkills = avgDrivingSkills,
        countReviews = countReviews
    )
}
