package com.example.gotogether.presentation.screens.detailed_trip_screen

import android.content.Context
import android.content.Intent
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gotogether.R
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import com.example.gotogether.domain.RouteCoordinates
import com.example.gotogether.domain.TripStatus
import com.example.gotogether.domain.trip_request.TripRequestStatus
import com.example.gotogether.presentation.screens.detailed_trip_screen.components.PassengersColumn
import com.example.gotogether.ui.theme.DarkGray
import com.example.gotogether.ui.theme.DarkGreen
import com.example.gotogether.ui.theme.MediumGray
import com.example.gotogether.ui.theme.Purple
import com.example.gotogether.ui.theme.PurpleGrey80
import com.example.gotogether.utils.extentions.roundTo2DecimalPlaces
import com.example.gotogether.utils.formatter.DistanceFormatter
import com.example.gotogether.utils.formatter.TimeFormatter
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.onSuccess

@Composable
fun DetailedTripScreen(
    detailedTripViewModel: DetailedTripViewModel = hiltViewModel<DetailedTripViewModel>(),
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val detailedTripState = detailedTripViewModel.state.collectAsState()

    val insertPassengerResult = detailedTripViewModel.insertPassengerResult
    val insertRequestResult = detailedTripViewModel.insertRequestResult
    val deletePassengerResult = detailedTripViewModel.deletePassengerResult
    val deleteRequestResult = detailedTripViewModel.deleteRequestResult

    LaunchedEffect(insertPassengerResult) {
        insertPassengerResult?.let {
            handleResult(it, context, navController)
        }
    }

    LaunchedEffect(insertRequestResult) {
        insertRequestResult?.let {
            handleResult(it, context, navController)
        }
    }

    LaunchedEffect(deletePassengerResult) {
        deletePassengerResult?.let {
            handleResult(it, context, navController)
        }
    }

    LaunchedEffect(deleteRequestResult) {
        deleteRequestResult?.let {
            handleResult(it, context, navController)
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (detailedTripState.value.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.run { align(Alignment.Center) }
            )
        } else {
            detailedTripState.value.detailedTrip?.onSuccess { trip ->
                val myRequestStatus =  detailedTripState.value.myRequest?.getOrNull()?.status
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
                        HorizontalDivider(
                            thickness = 2.dp,
                            color = PurpleGrey80,
                            modifier = Modifier.padding(16.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(
                                top = 8.dp,
                                bottom = 8.dp,
                                start = 16.dp,
                                end = 16.dp
                            )
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
                            modifier = Modifier.padding(
                                top = 8.dp,
                                bottom = 8.dp,
                                start = 16.dp,
                                end = 16.dp
                            )
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
                        if (trip.driverPhoneNumber != null) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(
                                    onClick = {
                                        val intent = Intent(Intent.ACTION_DIAL).apply {
                                            data = "tel:${trip.driverPhoneNumber}".toUri()
                                        }
                                        context.startActivity(intent)
                                    },
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
                        }
                        HorizontalDivider(
                            thickness = 8.dp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        detailedTripState.value.passengers?.onSuccess { passengers ->
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
                        Spacer(modifier = Modifier.height(90.dp))
                    }
                    if (now.isBefore(tripStartDateTime) &&
                        trip.status != TripStatus.CANCELED.status &&
                        myRequestStatus != TripRequestStatus.DECLINED.status
                        ) {
                        if (detailedTripState.value.isPassengerIsMe == true) {
                            DetailedTripActionButton(
                                buttonText = "Скасувати бронювання",
                                icon = Icons.Default.AutoMode,
                                buttonColor = DarkGreen,
                                onClick = { detailedTripViewModel.deletePassenger() },
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(16.dp)
                            )
                        } else if (myRequestStatus == TripRequestStatus.PENDING.status) {
                            DetailedTripActionButton(
                                buttonText = "Скасувати запит",
                                icon = Icons.Default.AccessTime,
                                buttonColor = DarkGreen,
                                onClick = { detailedTripViewModel.deleteRequest() },
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(16.dp)
                            )
                        } else if (detailedTripState.value.isPassengerIsMe == false &&
                            !trip.isFastConfirm &&
                            myRequestStatus != TripRequestStatus.DECLINED.status
                        ) {
                            DetailedTripActionButton(
                                buttonText = "Надіслати запит",
                                icon = Icons.Default.AccessTime,
                                buttonColor = Purple,
                                onClick = { detailedTripViewModel.createTripRequest() },
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(16.dp)
                            )
                        } else if (detailedTripState.value.isPassengerIsMe == false && trip.isFastConfirm) {
                            DetailedTripActionButton(
                                buttonText = "Забронювати",
                                icon = Icons.Default.AutoMode,
                                buttonColor = Purple,
                                onClick = { detailedTripViewModel.bookTrip() },
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(16.dp)
                            )
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

@Composable
fun DetailedTripActionButton(
    buttonText: String,
    icon: ImageVector,
    buttonColor: Color,
    onClick: suspend () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    Button(
        onClick = {
            scope.launch { onClick() }
        },
        modifier = modifier
            .height(50.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "icon",
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = buttonText,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

suspend fun handleResult(
    result: Result<ResponseDTO>,
    context: Context,
    navController: NavController? = null,
    successMessage: String = "Операція успішна",
    destination: String = "my_trips"
) {
    result.onSuccess {
        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        if(navController != null) navController.navigate(destination)
    }.onFailure { error ->
        Toast.makeText(context, error.message ?: "Сталася помилка", Toast.LENGTH_SHORT).show()
    }
}