package com.example.gotogether.presentation.screens.proposition_screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gotogether.R
import com.example.gotogether.domain.ChosenRoute
import com.example.gotogether.presentation.screens.search_trips_screen.components.CustomStyledDatePickerWithLimit
import com.example.gotogether.presentation.components.selector.SeatSelectorRow
import com.example.gotogether.presentation.components.selector.TripTypeSelectorRow
import com.example.gotogether.ui.theme.DarkGray
import com.example.gotogether.ui.theme.MediumGray
import com.example.gotogether.ui.theme.Purple
import com.example.gotogether.ui.theme.PurpleGrey80
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
@Preview(showBackground = true)
fun PropositionScreenPreview(modifier: Modifier = Modifier) {
    var showDatePicker = remember { mutableStateOf(false) }

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

    val isDialogOpenForSeats = remember { mutableStateOf(false) }
    val isDialogOpenForTripType = remember { mutableStateOf(false) }
    val selectedSeats = remember { mutableStateOf(ChosenRoute.seatsCount) }
    val isFastConfirm = remember { mutableStateOf(ChosenRoute.isFastConfirm) }
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

    LaunchedEffect(selectedHour, availableMinutes) {
        if (selectedMinute !in availableMinutes) {
            selectedMinute = availableMinutes.firstOrNull() ?: 0
        }
    }
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = {
                                    //  navController.navigate("choose_start_city")
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
                            .padding(start = 10.dp, end = 10.dp, top = 16.dp, bottom = 16.dp),
                        thickness = 2.dp,
                        color = PurpleGrey80
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = {
                                    //     navController.navigate("choose_end_city")
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
                            .padding(start = 10.dp, end = 10.dp, top = 16.dp, bottom = 16.dp),
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
                            .padding(start = 10.dp, end = 10.dp, top = 16.dp, bottom = 16.dp),
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
                            text = if(ChosenRoute.hourTrip == null) {"Час поїздки"}
                            else {
                                ChosenRoute.hourTrip.toString()
                            },
                            color = MediumGray,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp, top = 16.dp, bottom = 16.dp),
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
                            .padding(start = 10.dp, end = 10.dp, top = 16.dp, bottom = 16.dp),
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
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            Log.d("myLog", "${ChosenRoute.createInstantFromChosenRoute()}")
                            //   navController.navigate("my_trips")
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
                            text = "Шукати",
                            fontWeight = FontWeight.Medium
                        )
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
                        ChosenRoute.hourTrip = "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"
                    }
                )
            }
        )
        }
    SeatSelectorRow(isDialogOpenForSeats, selectedSeats)
    TripTypeSelectorRow(isDialogOpenForTripType, isFastConfirm)
}

@Composable
fun TimePicker(
    selectedHour: Int,
    selectedMinute: Int,
    onTimeSelected: (Int, Int) -> Unit,
) {
    val availableHours = getAvailableHours()

    var selectedHourState by remember { mutableStateOf(selectedHour) }
    var selectedMinuteState by remember { mutableStateOf(selectedMinute) }

    val availableMinutes by remember(selectedHourState) {
        mutableStateOf(getAvailableMinutesForHour(selectedHourState))
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 32.dp)
        ) {
            items(availableHours.size) { index ->
                val hour = availableHours[index]
                Text(
                    text = hour.toString().padStart(2, '0'),
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            selectedHourState = hour
                            selectedMinuteState = getAvailableMinutesForHour(hour).firstOrNull() ?: 0
                            onTimeSelected(selectedHourState, selectedMinuteState)
                        },
                    fontSize = if (hour == selectedHourState) 24.sp else 16.sp,
                    fontWeight = if (hour == selectedHourState) FontWeight.Bold else FontWeight.Normal,
                    color = if (hour == selectedHourState) Purple else Color.Gray
                )
            }
        }

        Text(":", fontSize = 24.sp, modifier = Modifier.padding(horizontal = 8.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 32.dp)
        ) {
            items(availableMinutes.size) { index ->
                val minute = availableMinutes[index]
                Text(
                    text = minute.toString().padStart(2, '0'),
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            selectedMinuteState = minute
                            onTimeSelected(selectedHourState, selectedMinuteState)
                        },
                    fontSize = if (minute == selectedMinuteState) 24.sp else 16.sp,
                    fontWeight = if (minute == selectedMinuteState) FontWeight.Bold else FontWeight.Normal,
                    color = if (minute == selectedMinuteState) Purple else Color.Gray
                )
            }
        }
    }
}

fun getAvailableHours(): List<Int> {
    val timeZone = TimeZone.of("Europe/Kyiv")
    val now = Clock.System.now().toLocalDateTime(timeZone)
    val today = now.date


    return if (ChosenRoute.dateTrip == today) {
        var startHour = now.hour
        val nowMinute = now.minute
        if (nowMinute >= 30) {
            startHour += 1
        }
        (startHour..23).toList()
    } else {
        (0..23).toList()
    }
}

fun getAvailableMinutesForHour(selectedHour: Int): List<Int> {
    val timeZone = TimeZone.of("Europe/Kyiv")
    val now = Clock.System.now().toLocalDateTime(timeZone)
    val nowHour = now.hour
    val nowMinute = now.minute
    val today = now.date
    return if (ChosenRoute.dateTrip == today && selectedHour == now.hour) {
        val startMinute = ((nowMinute + 30 + 9) / 10) * 10
        (startMinute..59 step 10).toList()
    } else {
        listOf(0, 10, 20, 30, 40, 50)
    }
}
