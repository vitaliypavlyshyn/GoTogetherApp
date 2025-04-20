package com.example.gotogether.presentation.screens.reviews_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gotogether.domain.ReviewRating
import com.example.gotogether.domain.review.Review
import com.example.gotogether.ui.theme.MediumGray
import com.example.gotogether.utils.converter.RatingConverter

@Composable
fun ReviewColumn(
    reviews: List<Review>,
    modifier: Modifier = Modifier
) {
    val ratingCounts = RatingConverter.countReviewRatings(reviews)

    Column(modifier = modifier) {
        ReviewRating.entries.forEach { rating ->
            Row(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = rating.type,
                    color = MediumGray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${ratingCounts[rating]}",
                    color = MediumGray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}