package com.example.gotogether.presentation.screens.my_trips_screen.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gotogether.domain.trip.Trip
import com.example.gotogether.presentation.screens.my_trips_screen.MyTripsViewModel.TripPreviewWithDriverReviewStatus
import com.example.gotogether.ui.theme.DarkGray
import com.example.gotogether.utils.formatter.TimeFormatter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun TripsListContent(
    tripPreviewWithDriverReviewStatus: Result<List<TripPreviewWithDriverReviewStatus>>?,
    navController: NavController,
    expandedDates: MutableMap<String, Boolean>,
) {
    tripPreviewWithDriverReviewStatus?.onSuccess { tripsPreview ->
        val today = LocalDate.now()

        val groupedTrips = tripsPreview.groupBy { tripPreview ->
            LocalDateTime.parse(tripPreview.trip.startTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                .toLocalDate()
        }

        val sortedGroupedTrips = groupedTrips.toSortedMap { date1, date2 ->
            when {
                date1 == today && date2 != today -> -1
                date2 == today && date1 != today -> 1
                else -> date2.compareTo(date1)
            }
        }

        LaunchedEffect(tripsPreview) {
            groupedTrips.keys.forEach { date ->
                expandedDates[date.toString()] = true
            }
        }

        if (tripsPreview.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 80.dp)
            ) {
                sortedGroupedTrips.forEach { (date, tripsForDate) ->
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    expandedDates[date.toString()] =
                                        !(expandedDates[date.toString()] ?: false)
                                }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (date == today) "Сьогодні" else TimeFormatter.formatFullUkrainianDateFromLocalDate(
                                    date
                                ),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = DarkGray
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                imageVector = if (expandedDates[date.toString()] == true) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = null
                            )
                        }
                    }

                    if (expandedDates[date.toString()] == true) {
                        val sortedTripsForDate = tripsForDate.sortedByDescending { tripPreview ->
                            LocalDateTime.parse(
                                tripPreview.trip.startTime,
                                DateTimeFormatter.ISO_LOCAL_DATE_TIME
                            )
                        }
                        items(sortedTripsForDate.size) { index ->
                            MyTripCard(
                                navController = navController,
                                tripPreviewWithReviewStatus = sortedTripsForDate[index]
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        } else {
            EmptyTripsPlaceholder(navController)
        }
    }?.onFailure {
        Text(
            text = "Помилка завантаження поїздок",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            color = Color.Red
        )
    }
}

