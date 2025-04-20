package com.example.gotogether.utils.converter

import com.example.gotogether.domain.ReviewRating
import com.example.gotogether.domain.review.Review

object RatingConverter {
    fun convertIntToReviewRation(rating: Int): ReviewRating {
        return when(rating) {
            1 -> ReviewRating.VERY_DISAPPOINTED
            2 -> ReviewRating.EXPECTATIONS_NOT_MET
            3 -> ReviewRating.OK
            4 -> ReviewRating.GOOD
            5 -> ReviewRating.PERFECTLY
            else -> ReviewRating.OK
        }
    }

    fun countReviewRatings(reviews: List<Review>): Map<ReviewRating, Int> {
        val counted = reviews
            .mapNotNull { it.rating }
            .map { RatingConverter.convertIntToReviewRation(it) }
            .groupingBy { it }
            .eachCount()

        return ReviewRating.entries.associateWith { counted[it] ?: 0 }
    }
}