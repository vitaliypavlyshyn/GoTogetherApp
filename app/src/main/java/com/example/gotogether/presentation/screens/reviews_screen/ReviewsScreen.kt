package com.example.gotogether.presentation.screens.reviews_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gotogether.presentation.screens.reviews_screen.components.ReviewColumn
import com.example.gotogether.presentation.screens.reviews_screen.components.ReviewInfo
import com.example.gotogether.ui.theme.MediumGray
import com.example.gotogether.ui.theme.Purple
import com.example.gotogether.ui.theme.PurpleGrey80
import com.example.gotogether.utils.extentions.roundTo2DecimalPlaces

@Composable
fun ReviewsScreen(
    reviewsState: ReviewsViewModel.ReviewState,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (reviewsState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.run { align(Alignment.Center) }
            )
        } else {
            reviewsState.rating?.onSuccess { rating ->
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                ) {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back",
                            tint = Purple
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Відгуки",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = MediumGray,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "star",
                            tint = Purple,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "${rating.avgRating?.roundTo2DecimalPlaces()}/5 - відгуків: ${rating.countReviews}",
                            color = MediumGray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(
                        thickness = 2.dp,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        color = PurpleGrey80
                    )
                    reviewsState.reviews?.onSuccess { reviews ->
                        ReviewColumn(reviews)
                        HorizontalDivider(
                            thickness = 8.dp
                        )
                        LazyColumn {
                            items(reviews.size) {
                                ReviewInfo(reviews[it])
                            }
                        }

                    }?.onFailure {
                        Text("Не вдалось завантажити відгуки")
                    }
                }
            }?.onFailure {
                Text("Не вдалось завантажити рейтинг")
            }
        }
    }
}