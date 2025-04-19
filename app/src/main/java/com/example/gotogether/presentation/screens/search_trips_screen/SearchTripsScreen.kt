package com.example.gotogether.presentation.screens.search_trips_screen

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gotogether.R
import com.example.gotogether.domain.ChosenRoute
import com.example.gotogether.presentation.screens.search_trips_screen.components.CustomStyledDatePickerWithLimit
import com.example.gotogether.presentation.screens.search_trips_screen.components.SeatSelectorRow
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
fun SearchTripsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val formatter = remember {
        DateTimeFormatter.ofPattern("d MMMM", Locale("uk", "UA"))
    }
    val timeZone = TimeZone.currentSystemDefault()
    val currentDate = Clock.System.now().toLocalDateTime(timeZone).date
    val formattedDate = remember(ChosenRoute.dateTrip) {
        when (ChosenRoute.dateTrip) {
            currentDate -> "Сьогодні"
            else -> ChosenRoute.dateTrip?.toJavaLocalDate()?.format(formatter) ?: ""
        }
    }
    val isDialogOpen = remember { mutableStateOf(false) }
    val selectedSeats = remember { mutableStateOf(ChosenRoute.seatsCount) }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        val colorText = remember {
            mutableStateOf(
                if (ChosenRoute.fromCityId == null) {
                    Color.Gray
                } else {
                    MediumGray
                }
            )
        }
        Image(
            painter = painterResource(R.drawable.trips_background2),
            contentDescription = "background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
        Text(
            text = "Знайдіть поїздку з попутниками та вирушайте у подорож",
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
                            color = colorText.value,
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
                            color = colorText.value,
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
                                    showDatePicker = true
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
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = {
                                    isDialogOpen.value = true
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
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            navController.navigate("trips_list")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Purple
                        ),
                        enabled = if (ChosenRoute.fromCityId == null || ChosenRoute.toCityId == null) {
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
    if (showDatePicker) {
        CustomStyledDatePickerWithLimit(
            onDateSelected = {
                ChosenRoute.dateTrip = it.toKotlinLocalDate()
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }

    SeatSelectorRow(isDialogOpen, selectedSeats)

}