package com.example.gotogether.presentation.screens.my_propositions_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gotogether.R
import com.example.gotogether.ui.theme.DarkGray
import com.example.gotogether.ui.theme.MediumGray
import com.example.gotogether.ui.theme.Purple
import com.example.gotogether.utils.formatter.TimeFormatter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MyPropositionsScreen(
    myPropositionsViewModel: MyPropositionsViewModel = hiltViewModel<MyPropositionsViewModel>(),
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val expandedDates = remember { mutableStateMapOf<String, Boolean>() }
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        val myPropositionsState = myPropositionsViewModel.state.collectAsState()
        LaunchedEffect(Unit) {
            myPropositionsViewModel.loadTrips()
        }
        if (myPropositionsState.value.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.run { align(Alignment.Center) }
            )
        } else {
            myPropositionsState.value.trips?.onSuccess { trips ->


                val today = LocalDate.now()

                val groupedTrips = trips.groupBy { trip ->
                    LocalDateTime.parse(trip.startTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                        .toLocalDate()
                }

                val sortedGroupedTrips = groupedTrips.toSortedMap { date1, date2 ->
                    when {
                        date1 == today && date2 != today -> -1
                        date2 == today && date1 != today -> 1
                        else -> date2.compareTo(date1)
                    }
                }

                LaunchedEffect(trips) {
                    groupedTrips.keys.forEach { date ->
                        expandedDates[date.toString()] = true
                    }
                }


                if (trips.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxSize()
                            .padding(bottom = 80.dp)
                    ) {
                        Text(
                            text = "Ваші пропозиції",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = MediumGray,
                            modifier = Modifier.padding(
                                top = 24.dp,
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 16.dp
                            )
                        )
                        LazyColumn(
                            modifier = Modifier
                                .background(Color.White)
                                .fillMaxSize()
                                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
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
                                            .padding(vertical = 8.dp)
                                            .animateContentSize(
                                                animationSpec = tween(
                                                    durationMillis = 300,
                                                    easing = FastOutSlowInEasing
                                                )
                                            ),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = if (date == LocalDate.now()) "Сьогодні" else TimeFormatter.formatFullUkrainianDateFromLocalDate(
                                                date
                                            ),
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = DarkGray
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Icon(
                                            imageVector = if (expandedDates[date.toString()] == true) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                            contentDescription = if (expandedDates[date.toString()] == true) "Згорнути" else "Розгорнути"
                                        )
                                    }
                                }

                                if (expandedDates[date.toString()] == true) {
                                    val sortedTripsForDate =
                                        tripsForDate.sortedByDescending { trip ->
                                            LocalDateTime.parse(
                                                trip.startTime,
                                                DateTimeFormatter.ISO_LOCAL_DATE_TIME
                                            )
                                        }
                                    items(sortedTripsForDate.size) {

                                        MyPropositionCard(
                                            navController = navController,
                                            trip = sortedTripsForDate[it],
                                            reviewableUsersMap = myPropositionsState.value.reviewableUsersMap
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Image(
                        painter = painterResource(R.drawable.my_propositions_background),
                        contentDescription = "background",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                    )

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 32.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "У вас немає створених поїздок",
                                    fontWeight = FontWeight.Medium,
                                    color = MediumGray,
                                )
                                Spacer(Modifier.height(8.dp))
                                Button(
                                    onClick = {
                                        navController.navigate("proposition")
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Purple
                                    )
                                ) {
                                    Text(
                                        text = "Створити поїздку"
                                    )
                                }
                            }
                        }
                    }
                }
            }?.onFailure {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Помилка завантаження інформації про створені поїздки")
                }
            }
        }
    }
}