package com.example.gotogether.presentation.screens.search_trips_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gotogether.domain.ChosenRoute
import com.example.gotogether.ui.theme.MediumGray
import com.example.gotogether.ui.theme.Purple
import com.example.gotogether.ui.theme.PurpleGrey80
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun SearchTripCard(
    navController: NavController,
    showDatePicker: MutableState<Boolean>,
    isDialogOpen: MutableState<Boolean>,
    selectedSeats: MutableState<Int>,
    modifier: Modifier = Modifier
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