package com.example.gotogether.presentation.components.selector

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gotogether.domain.ChosenRoute

@Composable
fun TripTypeSelectorRow(
    isDialogOpen: MutableState<Boolean>,
    isFastConfirm: MutableState<Boolean>
) {
    if (isDialogOpen.value) {
        AlertDialog(
            onDismissRequest = { isDialogOpen.value = false },
            title = { Text("Оберіть спосіб поїздки") },
            text = {
                Column {
                    listOf(
                        "Швидке підтвердження" to true,
                        "Бронювання" to false
                    ).forEach { (label, value) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    isFastConfirm.value = value
                                    ChosenRoute.isFastConfirm = value
                                    isDialogOpen.value = false
                                }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = isFastConfirm.value == value,
                                onClick = {
                                    isFastConfirm.value = value
                                    ChosenRoute.isFastConfirm = value
                                    isDialogOpen.value = false
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = label, fontSize = 16.sp)
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