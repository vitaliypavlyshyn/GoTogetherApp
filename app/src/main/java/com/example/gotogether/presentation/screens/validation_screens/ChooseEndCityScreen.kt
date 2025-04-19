package com.example.gotogether.presentation.screens.validation_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gotogether.domain.ChosenRoute
import com.example.gotogether.ui.theme.DarkGray
import com.example.gotogether.ui.theme.Purple
import com.example.gotogether.ui.theme.PurpleGrey80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseEndCityScreen(
    locationState: ChooseLocationViewModel.LocationsState,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (locationState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.run { align(Alignment.Center) }
            )
        } else {
            locationState.locations?.onSuccess { locations ->
                val citiesMap = locations
                    .groupBy { it.adminNameUk }
                    .toSortedMap()
                    .mapValues { entry ->
                        entry.value.map { it.cityNameUk }.distinct().sorted()
                    }

                val allCities = locations
                    .map { it.cityNameUk }
                    .distinct()
                    .sorted()

                var selectedRegion by remember { mutableStateOf<String?>(null) }
                var selectedCity by remember { mutableStateOf<String?>(null) }

                var expandedRegion by remember { mutableStateOf(false) }
                var expandedCity by remember { mutableStateOf(false) }

                val filteredCities = if (selectedRegion != null) {
                    citiesMap[selectedRegion] ?: emptyList()
                } else {
                    allCities
                }.filter { it != ChosenRoute.fromCityName}

                LaunchedEffect(selectedCity) {
                    selectedCity?.let { city ->
                        val location = locations.firstOrNull { it.cityNameUk == city }
                        if (location != null) {
                            selectedRegion = location.adminNameUk
                            ChosenRoute.toCityId = location.cityId
                            ChosenRoute.toCityName = location.cityNameUk
                            ChosenRoute.toCityAdminName = location.adminNameUk
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF8F8F8))
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        "Їхати до",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = DarkGray
                    )

                    ExposedDropdownMenuBox(
                        expanded = expandedRegion,
                        onExpandedChange = { expandedRegion = !expandedRegion }
                    ) {
                        OutlinedTextField(
                            value = selectedRegion ?: "",
                            onValueChange = {},
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            readOnly = true,
                            shape = RoundedCornerShape(16.dp),
                            label = { Text("Область") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedRegion)
                            },
                            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color(0xFF7C4DFF),
                                unfocusedBorderColor = PurpleGrey80,
                            )
                        )

                        ExposedDropdownMenu(
                            expanded = expandedRegion,
                            onDismissRequest = { expandedRegion = false }
                        ) {
                            citiesMap.keys.forEach { region ->
                                DropdownMenuItem(
                                    text = { Text(region) },
                                    onClick = {
                                        selectedRegion = region
                                        selectedCity = null
                                        expandedRegion = false
                                    }
                                )
                            }
                        }
                    }

                    ExposedDropdownMenuBox(
                        expanded = expandedCity,
                        onExpandedChange = { expandedCity = !expandedCity }
                    ) {
                        OutlinedTextField(
                            value = selectedCity ?: "",
                            onValueChange = {},
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            readOnly = true,
                            shape = RoundedCornerShape(16.dp),
                            label = { Text("Місто") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCity)
                            },
                            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color(0xFF7C4DFF),
                                unfocusedBorderColor = PurpleGrey80,
                            )
                        )

                        ExposedDropdownMenu(
                            expanded = expandedCity,
                            onDismissRequest = { expandedCity = false }
                        ) {
                            filteredCities.forEach { city ->
                                DropdownMenuItem(
                                    text = { Text(city) },
                                    onClick = {
                                        selectedCity = city
                                        expandedCity = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Purple)
                    ) {
                        Text("Підтвердити")
                    }
                }
            } ?.onFailure {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Помилка завантаження інформації про міст")
                }
            }
        }
    }

}