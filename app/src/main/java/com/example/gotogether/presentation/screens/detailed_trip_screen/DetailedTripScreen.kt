package com.example.gotogether.presentation.screens.detailed_trip_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoMode
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gotogether.R
import com.example.gotogether.domain.ChosenRoute
import com.example.gotogether.domain.RouteCoordinates
import com.example.gotogether.presentation.navigation.NavRoutes
import com.example.gotogether.ui.theme.DarkGray
import com.example.gotogether.ui.theme.DarkGreen
import com.example.gotogether.ui.theme.MediumGray
import com.example.gotogether.ui.theme.Purple
import com.example.gotogether.ui.theme.PurpleGrey80
import com.example.gotogether.utils.converter.TimeConverter

@Composable
fun DetailedTripScreen(
    detailedTripState: DetailedTripVewModel.DetailedTripState,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (detailedTripState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.run { align(Alignment.Center) }
            )
        } else {
            detailedTripState.detailedTrip?.onSuccess { trip ->
                val interactionSource = remember { MutableInteractionSource() }
                val isPressed = interactionSource.collectIsPressedAsState()
                val backgroundColor =
                    if (isPressed.value) Color(0x1A004D40) else Color.Transparent
                val contentColor = if (isPressed.value) DarkGreen.copy(alpha = 0.6f) else DarkGreen
                val formattedDate = remember(trip.startTime) {
                    mutableStateOf(TimeConverter.todMMMM(trip.startTime))
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 80.dp)
                            .verticalScroll(rememberScrollState())

                    ) {
                        IconButton(
                            onClick = {

                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "back",
                                tint = Purple
                            )
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text = formattedDate.value,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = MediumGray,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    onClick = {
                                        val route = RouteCoordinates(
                                            startLat = trip.startLat,
                                            startLng = trip.startLng,
                                            startCity = trip.startCity,
                                            endLat = trip.endLat,
                                            endLng = trip.endLng,
                                            endCity = trip.endCity
                                        )
                                        navController.currentBackStackEntry?.savedStateHandle?.set("coordinates", route)
                                        navController.navigate("google_maps")
                                    }
                                )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Column {
                                    Text(
                                        text = TimeConverter.toHHmm(trip.startTime),
                                        color = MediumGray,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = "71 км",
                                        color = MediumGray,
                                        fontSize = 10.sp,
                                    )
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = TimeConverter.toHHmm(trip.endTime),
                                    color = MediumGray,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Column(modifier = Modifier.padding(16.dp)) {
                                Column {
                                    Text(
                                        text = trip.startCity,
                                        color = MediumGray,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = trip.startRegion,
                                        color = MediumGray,
                                        fontSize = 10.sp,
                                    )
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                                Column {
                                    Text(
                                        text = trip.endCity,
                                        color = MediumGray,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = trip.endRegion,
                                        color = MediumGray,
                                        fontSize = 10.sp,
                                    )
                                }
                            }
                        }
                        HorizontalDivider(
                            thickness = 10.dp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.padding(start = 18.dp, end = 18.dp).height(32.dp)) {
                            Text(
                                text = "1 пасажир",
                                color = MediumGray,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "${trip.price} ₴",
                                color = MediumGray,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        HorizontalDivider(
                            thickness = 10.dp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp)
                                .clickable(
                                    onClick = {

                                    }
                                )
                        ) {
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
                                Image(
                                    painter = painterResource(R.drawable.test_avatar),
                                    contentDescription = "avatar",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column() {
                                Text(
                                    text = trip.driverFirstName,
                                    color = DarkGray,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (trip.avgDrivingSkills != null) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = "star",
                                            tint = Purple,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            text = "${trip.avgDrivingSkills} - ${trip.countReviews} відгуки",
                                            color = DarkGray,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Normal
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = "right",
                                tint = DarkGray
                            )
                        }
                        HorizontalDivider(
                            thickness = 2.dp,
                            color = PurpleGrey80,
                            modifier = Modifier.padding(16.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
                        ) {
                            if (!trip.isFastConfirm) {
                                Icon(
                                    imageVector = Icons.Default.AccessTime,
                                    contentDescription = "confirm_type",
                                    tint = MediumGray,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Бронювання буде підтверджено лише після того, як водій підтвердить запит",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MediumGray
                                )
                            } else {
                                Icon(
                                    Icons.Default.AutoMode,
                                    contentDescription = "confirm_type",
                                    tint = MediumGray,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Броюнвання буде підтверджене одразу",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MediumGray
                                )
                            }
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
                        ) {

                            Icon(
                                imageVector = Icons.Default.DirectionsCar,
                                contentDescription = "car_icon",
                                tint = MediumGray,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${trip.carMake} ${trip.carModel}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = MediumGray
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = { /* Дія */ },
                                interactionSource = interactionSource,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = backgroundColor,
                                    contentColor = contentColor
                                ),
                                border = BorderStroke(1.dp, DarkGreen),
                                modifier = Modifier.height(50.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Call,
                                    contentDescription = "call",
                                    tint = DarkGreen
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = "Подзвонити до ${trip.driverFirstName}",
                                    color = DarkGreen,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(
                            thickness = 10.dp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Пасажири",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Medium,
                            color = MediumGray,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp)
                                .clickable(
                                    onClick = {

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
                                    text = "Віталік",
                                    color = DarkGray,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "Львів → Шептицький (+1)",
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
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(
                            thickness = 10.dp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                                .clickable(
                                    onClick = {

                                    }
                                )
                        ) {

                            Icon(
                                imageVector = Icons.Default.WarningAmber,
                                contentDescription = "report",
                                tint = DarkGreen,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Поскаржитися на поїздку",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = DarkGreen
                            )
                        }
                        Spacer(modifier = Modifier.height(100.dp))
                    }

                    Button(
                        onClick = { /* дія */ },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp)
                            .height(50.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Purple
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (!trip.isFastConfirm) {
                                Icon(
                                    imageVector = Icons.Default.AccessTime,
                                    contentDescription = "confirm_type",
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Надіслати запит",
                                    fontWeight = FontWeight.Medium,
                                )
                            } else {
                                Icon(
                                    Icons.Default.AutoMode,
                                    contentDescription = "confirm_type",
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Забронювати",
                                    fontWeight = FontWeight.Medium,
                                )
                            }
                        }
                    }
                }
            }?.onFailure {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Помилка завантаження інформації про поїздку")
                }
            }
        }
    }
}