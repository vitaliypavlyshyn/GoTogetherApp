package com.example.gotogether.presentation.screens.my_trips_screen.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCarFilled
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavController
import com.example.gotogether.R
import com.example.gotogether.domain.TripStatus
import com.example.gotogether.presentation.screens.my_trips_screen.MyTripsViewModel.TripPreviewWithDriverReviewStatus
import com.example.gotogether.presentation.screens.trips_list_screen.components.checkTypeConfirmIcon
import com.example.gotogether.ui.theme.DarkGray
import com.example.gotogether.ui.theme.DarkGreen
import com.example.gotogether.ui.theme.MediumGray
import com.example.gotogether.ui.theme.Purple
import com.example.gotogether.ui.theme.PurpleGrey80
import com.example.gotogether.utils.extentions.roundTo2DecimalPlaces
import com.example.gotogether.utils.formatter.TimeFormatter

@Composable
fun MyTripCard(
    navController: NavController,
    tripPreviewWithReviewStatus: TripPreviewWithDriverReviewStatus,
    modifier: Modifier = Modifier,
) {
    val typeConfirmIcon = remember {
        mutableStateOf(checkTypeConfirmIcon(tripPreviewWithReviewStatus.trip.isFastConfirm))
    }
    if (tripPreviewWithReviewStatus.canLeaveReviewForDriver &&
        tripPreviewWithReviewStatus.trip.status != TripStatus.SCHEDULED.status &&
        tripPreviewWithReviewStatus.trip.status != TripStatus.CANCELED.status &&
        tripPreviewWithReviewStatus.wasPassenger
        ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .clickable(
                    onClick = {
                        navController.navigate("reviewable_list_trip/${tripPreviewWithReviewStatus.trip.tripId}")
                    }
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.StarBorder,
                contentDescription = "rating",
                tint = Purple
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Залишити відгук",
                color = Purple,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
    Card(
        modifier = Modifier
            .width(400.dp)
            .clickable(
                onClick = {
                    navController.navigate("detailed_trip/${tripPreviewWithReviewStatus.trip.tripId}")
                }
            ),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, PurpleGrey80),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column() {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (tripPreviewWithReviewStatus.wasPassenger) {
                        tripPreviewWithReviewStatus.trip.status
                    } else {
                        tripPreviewWithReviewStatus.requestStatus ?: "Очікує підтвердження"
                    },
                    color = MediumGray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp,
                color = PurpleGrey80
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = TimeFormatter.toHHmm(tripPreviewWithReviewStatus.trip.startTime),
                        color = DarkGray,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = TimeFormatter.toHHmm(tripPreviewWithReviewStatus.trip.endTime),
                        color = DarkGray,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = tripPreviewWithReviewStatus.trip.startLocationCity,
                        color = DarkGray,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = tripPreviewWithReviewStatus.trip.endLocationCity,
                        color = DarkGray,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${tripPreviewWithReviewStatus.trip.price} ₴",
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 2.dp,
            color = PurpleGrey80
        )
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.DirectionsCarFilled,
                contentDescription = "car",
                tint = DarkGray
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .border(
                        width = 1.dp,
                        color = DarkGreen,
                        shape = CircleShape
                    )
                    .padding(4.dp)
                    .clip(CircleShape)
            ) {
                val bitmap = tripPreviewWithReviewStatus.trip.driverPicture?.let {
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
            Spacer(modifier = Modifier.width(8.dp))
            Column() {
                Text(
                    text = tripPreviewWithReviewStatus.trip.driverFirstName,
                    color = DarkGray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                if (tripPreviewWithReviewStatus.trip.avgRating != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "star",
                            tint = Purple,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = tripPreviewWithReviewStatus.trip.avgRating.roundTo2DecimalPlaces()
                                .toString(),
                            color = DarkGray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = typeConfirmIcon.value,
                contentDescription = "access",
                tint = DarkGray,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

