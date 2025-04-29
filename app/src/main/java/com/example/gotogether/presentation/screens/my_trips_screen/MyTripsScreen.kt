package com.example.gotogether.presentation.screens.my_trips_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gotogether.presentation.screens.my_trips_screen.components.TripsListContent
import com.example.gotogether.ui.theme.DarkGray
import com.example.gotogether.ui.theme.Purple

@Composable
fun MyTripsScreen(
    myTripsViewModel: MyTripsViewModel = hiltViewModel<MyTripsViewModel>(),
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val expandedDates = remember { mutableStateMapOf<String, Boolean>() }
    val myTripsState = myTripsViewModel.state.collectAsState()
    var selectedTabIndex by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        myTripsViewModel.loadTrips()
    }


    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (myTripsState.value.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = Color.White,
                    contentColor = Purple,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            color = Purple
                        )
                    }
                ) {
                    Tab(
                        selected = selectedTabIndex == 0,
                        onClick = { selectedTabIndex = 0 },
                        text = {
                            Text(
                                text = "Мої бронювання",
                                color = if (selectedTabIndex == 0) Purple else DarkGray
                            )
                        }
                    )
                    Tab(
                        selected = selectedTabIndex == 1,
                        onClick = { selectedTabIndex = 1 },
                        text = {
                            Text(
                                text = "Мої запити",
                                color = if (selectedTabIndex == 1) Purple else DarkGray
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                when (selectedTabIndex) {
                    0 -> {
                        TripsListContent(
                            tripPreviewWithDriverReviewStatus = myTripsState.value.bookedTrips,
                            navController = navController,
                            expandedDates = expandedDates
                        )
                    }

                    1 -> {
                        TripsListContent(
                            tripPreviewWithDriverReviewStatus = myTripsState.value.requests,
                            navController = navController,
                            expandedDates = expandedDates
                        )
                    }
                }
            }
        }
    }
}

