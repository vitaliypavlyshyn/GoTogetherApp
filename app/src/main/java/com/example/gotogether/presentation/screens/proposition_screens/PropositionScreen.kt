package com.example.gotogether.presentation.screens.proposition_screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gotogether.R
import com.example.gotogether.data.google_maps_route.getDirectionResponse
import com.example.gotogether.domain.ChosenRoute
import com.example.gotogether.presentation.components.selector.SeatSelectorRow
import com.example.gotogether.presentation.components.selector.TripTypeSelectorRow
import com.example.gotogether.presentation.screens.search_trips_screen.components.CustomStyledDatePickerWithLimit
import com.example.gotogether.ui.theme.DarkGray
import com.example.gotogether.ui.theme.MediumGray
import com.example.gotogether.ui.theme.Purple
import com.example.gotogether.ui.theme.PurpleGrey80
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun PropositionScreen(
    propositionViewModel: PropositionViewModel = hiltViewModel<PropositionViewModel>(),
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val propositionState = propositionViewModel.state.collectAsState()
    var showDatePicker = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val timeZone = TimeZone.of("Europe/Kyiv")
    val formatter = remember {
        DateTimeFormatter.ofPattern("d MMMM", Locale("uk", "UA"))
    }
    val currentDate = Clock.System.now().toLocalDateTime(timeZone).date
    val formattedDate = remember(ChosenRoute.dateTrip) {
        when (ChosenRoute.dateTrip) {
            currentDate -> "Сьогодні"
            else -> ChosenRoute.dateTrip.toJavaLocalDate().format(formatter)
        }
    }
    val insertResult = propositionViewModel.insertResult

    LaunchedEffect(insertResult) {
        insertResult?.onSuccess { res ->
            if (res.isSuccess) {
                navController.navigate("my_propositions")
            }
            Toast.makeText(context, res.message, Toast.LENGTH_SHORT).show()
        }?.onFailure { res ->
            Toast.makeText(context, res.message, Toast.LENGTH_SHORT).show()
        }
    }
    val isDialogOpenForSeats = remember { mutableStateOf(false) }
    val isDialogOpenForTripType = remember { mutableStateOf(false) }
    val selectedSeats = remember { mutableStateOf(ChosenRoute.seatsCount) }
    val isFastConfirm = remember { mutableStateOf(ChosenRoute.isFastConfirm) }
    val price = remember { mutableStateOf(ChosenRoute.price) }
    var priceInput by remember { mutableStateOf(ChosenRoute.price?.toString() ?: "") }
    var isPriceValid by remember { mutableStateOf(true) }

    val isToday = ChosenRoute.dateTrip == currentDate

    var selectedHour by remember { mutableStateOf(0) }
    var selectedMinute by remember { mutableStateOf(0) }

    val isTimeDialogOpen = remember { mutableStateOf(false) }

    val now = Clock.System.now()
        .toLocalDateTime(timeZone)
    val availableMinutes = remember(isToday, now, selectedHour) {
        if (isToday && selectedHour == now.hour) {
            val minutesFromNow = (now.minute + 30) % 60
            (0..59 step 10).filter { it >= minutesFromNow }
        } else {
            (0..59 step 10).toList()
        }
    }
    LaunchedEffect(navController.currentBackStackEntry) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            if (backStackEntry.destination.route == "proposition") {
                propositionViewModel.refreshUser()
            }
        }
    }
    LaunchedEffect(selectedHour, availableMinutes) {
        if (selectedMinute !in availableMinutes) {
            selectedMinute = availableMinutes.firstOrNull() ?: 0
        }
    }
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (propositionState.value.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.run { align(Alignment.Center) }
            )
        } else {
            propositionState.value.user?.onSuccess { user ->
                Box(
                    modifier = modifier
                        .fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(R.drawable.trips_background),
                        contentDescription = "background",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                    Text(
                        text = "Створіть поїздку та вирушайте з попутниками у подорож",
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        color = DarkGray,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 50.dp, start = 20.dp, end = 20.dp)
                    )
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                        ) {

                            Column(modifier = Modifier.padding(16.dp)) {
                                if (user.carId == null) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Щоб створити поїздку, потрібно додати транспортний засіб",
                                            color = MediumGray,
                                            fontWeight = FontWeight.Medium,
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Button(
                                        onClick = {
                                            navController.navigate("choose_car")
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Purple
                                        )
                                    ) {
                                        Text(
                                            text = "Додати транспортний засіб",
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                } else {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable(
                                                onClick = {
                                                    navController.navigate("choose_start_city")
                                                }
                                            )
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Adjust,
                                            contentDescription = "circle",
                                            tint = MediumGray
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = if (ChosenRoute.fromCityId == null) "Виїжджаєте з"
                                            else ChosenRoute.fromCityName + ", " + ChosenRoute.fromCityAdminName,
                                            color = MediumGray,
                                            fontWeight = FontWeight.Medium,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    HorizontalDivider(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = 10.dp,
                                                end = 10.dp,
                                                top = 16.dp,
                                                bottom = 16.dp
                                            ),
                                        thickness = 2.dp,
                                        color = PurpleGrey80
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable(
                                                onClick = {
                                                    navController.navigate("choose_end_city")
                                                }
                                            )
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Adjust,
                                            contentDescription = "circle",
                                            tint = MediumGray
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = if (ChosenRoute.toCityId == null) "Прямуєте до"
                                            else ChosenRoute.toCityName + ", " + ChosenRoute.toCityAdminName,
                                            color = MediumGray,
                                            fontWeight = FontWeight.Medium,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    HorizontalDivider(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = 10.dp,
                                                end = 10.dp,
                                                top = 16.dp,
                                                bottom = 16.dp
                                            ),
                                        thickness = 2.dp,
                                        color = PurpleGrey80
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable(
                                                onClick = {
                                                    showDatePicker.value = true
                                                }
                                            )
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.CalendarMonth,
                                            contentDescription = "calendar",
                                            tint = MediumGray
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = formattedDate,
                                            color = MediumGray,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                    HorizontalDivider(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = 10.dp,
                                                end = 10.dp,
                                                top = 16.dp,
                                                bottom = 16.dp
                                            ),
                                        thickness = 2.dp,
                                        color = PurpleGrey80
                                    )
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                isTimeDialogOpen.value = true
                                            },
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.AccessTime,
                                            contentDescription = "access time",
                                            tint = MediumGray
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = if (ChosenRoute.hourTrip == null) {
                                                "Час поїздки"
                                            } else {
                                                ChosenRoute.hourTrip.toString()
                                            },
                                            color = MediumGray,
                                            fontWeight = FontWeight.Medium,
                                        )
                                    }
                                    HorizontalDivider(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = 10.dp,
                                                end = 10.dp,
                                                top = 16.dp,
                                                bottom = 16.dp
                                            ),
                                        thickness = 2.dp,
                                        color = PurpleGrey80
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable(
                                                onClick = {
                                                    isDialogOpenForSeats.value = true
                                                }
                                            )
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = "person",
                                            tint = MediumGray
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "${selectedSeats.value} особа (-и)",
                                            color = MediumGray,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                    HorizontalDivider(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = 10.dp,
                                                end = 10.dp,
                                                top = 16.dp,
                                                bottom = 16.dp
                                            ),
                                        thickness = 2.dp,
                                        color = PurpleGrey80
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable(
                                                onClick = {
                                                    isDialogOpenForTripType.value = true
                                                }
                                            )
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.AccessTimeFilled,
                                            contentDescription = "access time filled",
                                            tint = MediumGray
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = if (isFastConfirm.value) "Швидке підтвердження" else "Бронювання",
                                            color = MediumGray,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                    HorizontalDivider(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = 10.dp,
                                                end = 10.dp,
                                                top = 16.dp,
                                                bottom = 16.dp
                                            ),
                                        thickness = 2.dp,
                                        color = PurpleGrey80
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Column() {
                                            TextField(
                                                value = priceInput,
                                                onValueChange = { newValue ->
                                                    priceInput = newValue.filter { it.isDigit() }
                                                    val intPrice = priceInput.toIntOrNull()
                                                    if (intPrice != null) {
                                                        isPriceValid = intPrice in 50..500
                                                        ChosenRoute.price = intPrice
                                                        price.value = intPrice
                                                    } else {
                                                        isPriceValid = false
                                                        ChosenRoute.price = null
                                                        price.value = null
                                                    }
                                                },
                                                placeholder = {
                                                    Text(text = "Вкажіть ціну")
                                                },
                                                isError = !isPriceValid,
                                                modifier = Modifier.fillMaxWidth(),
                                                singleLine = true,
                                                keyboardOptions = KeyboardOptions(
                                                    keyboardType = KeyboardType.Number
                                                ),
                                                colors = TextFieldDefaults.colors(
                                                    unfocusedContainerColor = PurpleGrey80.copy(
                                                        alpha = 0.1f
                                                    ),
                                                    focusedContainerColor = PurpleGrey80.copy(alpha = 0.2f),
                                                    errorContainerColor = Color.Red.copy(alpha = 0.1f)
                                                )
                                            )
                                            if (!isPriceValid) {
                                                Text(
                                                    text = "Ціна повинна бути від 50 до 500 грн",
                                                    color = Color.Red,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    modifier = Modifier.padding(
                                                        start = 16.dp,
                                                        top = 4.dp
                                                    )
                                                )
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Button(
                                        onClick = {
                                            scope.launch {
                                                propositionViewModel.createTrip(user.userUuid)
                                            }
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Purple
                                        ),
                                        enabled = if (ChosenRoute.fromCityId == null ||
                                            ChosenRoute.toCityId == null ||
                                            ChosenRoute.hourTrip == null
                                        ) {
                                            false
                                        } else {
                                            true
                                        }
                                    ) {
                                        Text(
                                            text = "Створити поїздку",
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                if (showDatePicker.value) {
                    CustomStyledDatePickerWithLimit(
                        onDateSelected = {
                            ChosenRoute.dateTrip = it.toKotlinLocalDate()
                            showDatePicker.value = false
                        },
                        onDismiss = { showDatePicker.value = false }
                    )
                }
                if (isTimeDialogOpen.value) {
                    AlertDialog(
                        onDismissRequest = { isTimeDialogOpen.value = false },
                        confirmButton = {
                            TextButton(onClick = {
                                isTimeDialogOpen.value = false
                            }) {
                                Text("ОК")
                            }
                        },
                        title = {
                            Text("Оберіть час поїздки")
                        },
                        text = {
                            TimePicker(
                                selectedHour = selectedHour,
                                selectedMinute = selectedMinute,
                                onTimeSelected = { hour, minute ->
                                    selectedHour = hour
                                    selectedMinute = minute
                                    ChosenRoute.hourTrip = "${hour.toString().padStart(2, '0')}:${
                                        minute.toString().padStart(2, '0')
                                    }"
                                }
                            )
                        }
                    )
                }
                SeatSelectorRow(isDialogOpenForSeats, selectedSeats)
                TripTypeSelectorRow(isDialogOpenForTripType, isFastConfirm)
            }?.onFailure {

            }
        }
    }
}