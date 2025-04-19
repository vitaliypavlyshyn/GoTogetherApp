package com.example.gotogether.presentation.screens.search_trips_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import com.example.gotogether.domain.ChosenRoute
import com.example.gotogether.ui.theme.MediumGray

@Composable
fun SeatSelectorRow(
    isDialogOpen: MutableState<Boolean>,
    selectedSeats: MutableState<Int>)
{
    if (isDialogOpen.value) {
        AlertDialog(
            onDismissRequest = { isDialogOpen.value = false },
            title = { Text("Оберіть кількість місць") },
            text = {
                Column {
                    (1..4).forEach { count ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedSeats.value = count
                                    ChosenRoute.seatsCount = count
                                    isDialogOpen.value = false
                                }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedSeats.value == count,
                                onClick = {
                                    selectedSeats.value = count
                                    ChosenRoute.seatsCount = count
                                    isDialogOpen.value = false
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "$count", fontSize = 16.sp)
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { isDialogOpen.value = false }) {
                    Text("Скасувати")
                }
            }
        )
    }
}