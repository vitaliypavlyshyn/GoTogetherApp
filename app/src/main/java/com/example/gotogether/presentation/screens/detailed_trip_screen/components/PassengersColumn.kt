package com.example.gotogether.presentation.screens.detailed_trip_screen.components

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gotogether.R
import com.example.gotogether.domain.trip_passenger.TripPassenger
import com.example.gotogether.ui.theme.DarkGray
import com.example.gotogether.ui.theme.MediumGray

@Composable
fun PassengersColumn(
    passengers: List<TripPassenger>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Пасажири",
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            color = MediumGray,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )
        repeat(passengers.size) { index ->
            Row() {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                        .clickable(
                            onClick = {
                                navController.navigate("user_profile/${passengers[index].passengerUuid}")
                            }
                        )
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)

                    ) {
                        Image(
                            painter = painterResource(R.drawable.tuqui),
                            contentDescription = "avatar",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column() {
                        Text(
                            text = passengers[index].firstName,
                            color = DarkGray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "${passengers[index].seatsBooked} особа (-и)",
                            color = DarkGray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "right",
                        tint = DarkGray
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
