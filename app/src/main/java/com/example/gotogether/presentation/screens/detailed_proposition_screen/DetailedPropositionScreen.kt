package com.example.gotogether.presentation.screens.detailed_proposition_screen

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gotogether.R
import com.example.gotogether.domain.RouteCoordinates
import com.example.gotogether.domain.TripStatus
import com.example.gotogether.domain.trip_request.TripRequestStatus
import com.example.gotogether.presentation.screens.detailed_proposition_screen.components.PassengersColumn
import com.example.gotogether.presentation.screens.detailed_proposition_screen.components.RequestersColumn
import com.example.gotogether.presentation.screens.detailed_trip_screen.DetailedTripActionButton
import com.example.gotogether.presentation.screens.detailed_trip_screen.handleResult
import com.example.gotogether.ui.theme.DarkGray
import com.example.gotogether.ui.theme.DarkGreen
import com.example.gotogether.ui.theme.MediumGray
import com.example.gotogether.ui.theme.Purple
import com.example.gotogether.utils.extentions.roundTo2DecimalPlaces
import com.example.gotogether.utils.formatter.DistanceFormatter
import com.example.gotogether.utils.formatter.TimeFormatter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun DetailedPropositionScreen(
    detailedPropositionViewModel: DetailedPropositionViewModel = hiltViewModel<DetailedPropositionViewModel>(),
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val detailedPropositionState = detailedPropositionViewModel.state.collectAsState()

    val updateTripResult = detailedPropositionViewModel.updateTripResult
    val updateTripRequestResult = detailedPropositionViewModel.updateTripRequestResult


    LaunchedEffect(updateTripResult) {
        updateTripResult?.let {
            handleResult(it, context, navController, successMessage = "Скасування поїздки успішно", destination = "my_propositions")
        }
    }

    LaunchedEffect(updateTripRequestResult) {
        updateTripRequestResult?.let {
            handleResult(it, context, successMessage = "Запит користувача оброблено")
        }
    }



    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (detailedPropositionState.value.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.run { align(Alignment.Center) }
            )
        } else {
            detailedPropositionState.value.detailedTrip?.onSuccess { trip ->
                val context = LocalContext.current
                val interactionSource = remember { MutableInteractionSource() }
                val isPressed = interactionSource.collectIsPressedAsState()
                val backgroundColor =
                    if (isPressed.value) Color(0x1A004D40) else Color.Transparent
                val contentColor = if (isPressed.value) DarkGreen.copy(alpha = 0.6f) else DarkGreen
                val formattedDate = remember(trip.startTime) {
                    mutableStateOf(TimeFormatter.formatDateWithTodayCheck(trip.startTime))
                }
                val tripStartDateTime = LocalDateTime.parse(trip.startTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                val now = LocalDateTime.now()

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
                            modifier = Modifier.padding(16.dp),
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
                                        navController.currentBackStackEntry?.savedStateHandle?.set(
                                            "coordinates",
                                            route
                                        )
                                        navController.navigate("google_maps")
                                    }
                                )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Column {
                                    Text(
                                        text = TimeFormatter.toHHmm(trip.startTime),
                                        color = MediumGray,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = "${DistanceFormatter.formatDistanceInKm(trip.distanceInMeters)} км",
                                        color = MediumGray,
                                        fontSize = 10.sp,
                                    )
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = TimeFormatter.toHHmm(trip.endTime),
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
                            thickness = 8.dp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier
                            .padding(start = 18.dp, end = 18.dp)
                            .height(32.dp)) {
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
                            thickness = 8.dp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp)
                                .clickable(
                                    onClick = {
                                        navController.navigate("user_profile/${trip.driverUuid}")
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
                                    if (trip.avgRating != null) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = "star",
                                            tint = Purple,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            text = "${trip.avgRating.roundTo2DecimalPlaces()}/5 - ${trip.countReviews} відгуки",
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
                        Spacer(modifier = Modifier.height(16.dp))

                        HorizontalDivider(
                            thickness = 8.dp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        detailedPropositionState.value.passengers?.onSuccess { passengers ->
                            if (passengers.isNotEmpty()) {
                                Text(
                                    text = "Пасажири",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MediumGray,
                                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                                )
                                PassengersColumn(
                                    passengers = passengers,
                                    navController = navController
                                )
                            } else {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Пасажирів немає",
                                        fontWeight = FontWeight.Medium,
                                        color = MediumGray,
                                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                                    )
                                }
                            }
                        }?.onFailure {
                            Text(
                                "Не вдалось завантажити дані про пасажирів"
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(
                            thickness = 8.dp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        detailedPropositionState.value.tripRequests?.onSuccess { requests ->
                            val filteredRequests = requests.filter {
                                it.status == TripRequestStatus.PENDING.status &&
                                        it.requestedSeats <= trip.availableSeats
                            }
                            if (filteredRequests.isNotEmpty()) {
                                Text(
                                    text = "Запити",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MediumGray,
                                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                                )
                                RequestersColumn(
                                    requesters = filteredRequests,
                                    navController = navController,
                                    onFirstClick = { requestId ->
                                        detailedPropositionViewModel.manageRequest(
                                            requestId, TripRequestStatus.ACCEPTED
                                        )
                                    },
                                    onSecondClick = { requestId ->
                                        detailedPropositionViewModel.manageRequest(
                                            requestId, TripRequestStatus.DECLINED
                                        )
                                    }
                                )
                            }else {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Запитів немає",
                                        fontWeight = FontWeight.Medium,
                                        color = MediumGray,
                                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                                    )
                                }
                            }
                        }?.onFailure {
                            Text(
                                "Не вдалось завантажити дані про пасажирів"
                            )
                        }
                        Spacer(modifier = Modifier.height(90.dp))
                    }
                    if (now.isBefore(tripStartDateTime) && trip.status != TripStatus.CANCELED.status) {
                            DetailedTripActionButton(
                                buttonText = "Скасувати поїздку",
                                icon = Icons.Default.Cancel,
                                buttonColor = DarkGreen,
                                onClick = { detailedPropositionViewModel.cancelTrip() },
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(16.dp)
                            )

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

