package com.example.gotogether.presentation.screens.validation_screens.choose_car_screen

import android.util.Log
import android.widget.Toast
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gotogether.data.user.UpdateUserRequestDTO
import com.example.gotogether.domain.ChosenRoute
import com.example.gotogether.presentation.screens.validation_screens.choose_cities_screens.LocationViewModel
import com.example.gotogether.ui.theme.DarkGray
import com.example.gotogether.ui.theme.Purple
import com.example.gotogether.ui.theme.PurpleGrey80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseCarScreen(
    carViewModel: CarViewModel = hiltViewModel<CarViewModel>(),
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val myCarState = carViewModel.state.collectAsState()
    val context = LocalContext.current
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (myCarState.value.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.run { align(Alignment.Center) }
            )
        } else {
            myCarState.value.cars?.onSuccess { cars ->
                val carsMap = cars
                    .groupBy { it.make }
                    .toSortedMap()
                    .mapValues { entry ->
                        entry.value.map { it.model }.distinct().sorted()
                    }

                val allCars = cars
                    .map { it.model }
                    .distinct()
                    .sorted()

                var selectedCarId by remember { mutableStateOf<Long?>(-1) }

                var selectedMake by remember { mutableStateOf<String?>(null) }
                var selectedModel by remember { mutableStateOf<String?>(null) }

                var expandedMake by remember { mutableStateOf(false) }
                var expandedModel by remember { mutableStateOf(false) }

                val filteredCars = if (selectedMake != null) {
                    carsMap[selectedMake] ?: emptyList()
                } else {
                    allCars
                }.filter { it != ChosenRoute.fromCityName}

                LaunchedEffect(selectedMake, selectedModel) {
                    if (selectedMake != null && selectedModel != null) {
                        val car = cars.firstOrNull { it.make == selectedMake && it.model == selectedModel }
                        selectedCarId = car?.carId
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF8F8F8))
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
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
                    Text(
                        "Обрати транспортний засіб",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = DarkGray
                    )

                    ExposedDropdownMenuBox(
                        expanded = expandedMake,
                        onExpandedChange = { expandedMake = !expandedMake }
                    ) {
                        OutlinedTextField(
                            value = selectedMake ?: "",
                            onValueChange = {},
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            readOnly = true,
                            shape = RoundedCornerShape(16.dp),
                            label = { Text("Виробник") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMake)
                            },
                            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color(0xFF7C4DFF),
                                unfocusedBorderColor = PurpleGrey80,
                            )
                        )

                        ExposedDropdownMenu(
                            expanded = expandedMake,
                            onDismissRequest = { expandedMake = false }
                        ) {
                            carsMap.keys.forEach { make ->
                                DropdownMenuItem(
                                    text = { Text(make) },
                                    onClick = {
                                        selectedMake = make
                                        selectedModel = null
                                        expandedMake = false
                                    }
                                )
                            }
                        }
                    }

                    ExposedDropdownMenuBox(
                        expanded = expandedModel,
                        onExpandedChange = { if (selectedMake != null) expandedModel = !expandedModel }
                    ) {
                        OutlinedTextField(
                            value = selectedModel ?: "",
                            onValueChange = {},
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            readOnly = true,
                            shape = RoundedCornerShape(16.dp),
                            label = {
                                Text(if (selectedMake == null) "Спочатку оберіть виробника" else "Модель")
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedModel)
                            },
                            enabled = selectedMake != null,
                            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color(0xFF7C4DFF),
                                unfocusedBorderColor = PurpleGrey80,
                                disabledBorderColor = PurpleGrey80,
                                disabledTextColor = Color.Gray,
                                disabledLabelColor = Color.Gray
                            )
                        )

                        if (selectedMake != null) {
                            ExposedDropdownMenu(
                                expanded = expandedModel,
                                onDismissRequest = { expandedModel = false }
                            ) {
                                filteredCars.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedModel = city
                                            expandedModel = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            myCarState.value.user?.onSuccess { user ->
                                carViewModel.updateCar(user.userUuid, selectedCarId)
                            }?.onFailure {
                                Toast.makeText(context, "Помилка додавання транспортного засобу", Toast.LENGTH_SHORT).show()
                            }
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
                    Text(text = "Помилка завантаження інформації про машини")
                }
            }
        }
    }

}