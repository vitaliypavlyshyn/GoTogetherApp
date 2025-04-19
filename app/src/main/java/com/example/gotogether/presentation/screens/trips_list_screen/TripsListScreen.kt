package com.example.gotogether.presentation.screens.trips_list_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gotogether.domain.ChosenRoute
import com.example.gotogether.domain.TripStatus
import com.example.gotogether.presentation.screens.trips_list_screen.components.TripCard
import com.example.gotogether.presentation.screens.trips_list_screen.components.TripCardPreview
import com.example.gotogether.ui.theme.Light
import com.example.gotogether.ui.theme.MediumGray
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun TripsListScreen(
    tripsListState: TripsListViewModel.TripsState,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val formatter = DateTimeFormatter.ISO_DATE_TIME
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (tripsListState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.run { align(Alignment.Center) }
            )
        } else {
            tripsListState.trips?.onSuccess { trips ->
                val filteredTrips = trips.filter {
                    it.status == TripStatus.SCHEDULED.status
                }.sortedBy {
                    LocalDateTime.parse(it.startTime, formatter)
                }
                Column(modifier = Modifier.background(Color.White)) {
                    Row(
                        modifier = Modifier.padding(top = 16.dp).fillMaxWidth().background(Color.White)
                            .drawBehind {
                                val strokeWidth = 2.dp.toPx()
                                val y = size.height - strokeWidth / 2
                                drawLine(
                                    color = Light,
                                    start = Offset(0f, y),
                                    end = Offset(size.width, y),
                                    strokeWidth = strokeWidth
                                )
                            },
                        horizontalArrangement = Arrangement.Absolute.Center,
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Спільні поїздки",
                                color = MediumGray,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Text(
                                text = filteredTrips.size.toString(),
                                color = MediumGray,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                LazyColumn(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(
                        filteredTrips.size
                    ) { index ->
                        TripCard(
                            trip = filteredTrips[index]
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                }
            }?.onFailure {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Помилка завантаження інформації про поїздки")
                }
            }
        }
    }
}