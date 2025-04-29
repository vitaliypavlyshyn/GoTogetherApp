package com.example.gotogether.presentation.screens.reviews_screen.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gotogether.R
import com.example.gotogether.domain.review.Review
import com.example.gotogether.ui.theme.DarkGreen
import com.example.gotogether.ui.theme.MediumGray
import com.example.gotogether.ui.theme.PurpleGrey80
import com.example.gotogether.utils.converter.RatingConverter
import com.example.gotogether.utils.formatter.TimeFormatter

@Composable
fun ReviewInfo(
    review: Review,
    modifier: Modifier = Modifier
) {
    val rating = RatingConverter.convertIntToReviewRation(review.rating!!)
    val commentFormatDate = TimeFormatter.formatToMonthYear(review.createdAt)
    Column(
        modifier = Modifier.background(Color.White).padding(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = review.reviewerFirstName,
                color = MediumGray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)

            ) {
                val bitmap = review.reviewerPicture?.let {
                    BitmapFactory.decodeByteArray(it, 0, it.size)
                }?.asImageBitmap()

                Image(
                    bitmap = bitmap
                        ?: ImageBitmap.imageResource(id = R.drawable.test_avatar),
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = rating.type,
            color = MediumGray,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = review.comment,
            color = MediumGray,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = commentFormatDate,
            color = MediumGray,
            fontSize = 12.sp,
        )
    }
    HorizontalDivider(
        thickness = 2.dp,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
        color = PurpleGrey80
    )
}