package com.example.gotogether.presentation.screens.search_trips_screen.components


import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toLocalDateTime
import java.time.LocalDate
import kotlin.time.Duration.Companion.days


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomStyledDatePickerWithLimit(
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    val today = Clock.System.now()
    val twoWeeksLater = today + 14.days

    val todayMillis = today.toEpochMilliseconds()
    val twoWeeksLaterMillis = twoWeeksLater.toEpochMilliseconds()
    val timeZone = TimeZone.currentSystemDefault()
    val currentDate = Clock.System.now().toLocalDateTime(timeZone).date
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }


    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = todayMillis,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val pickedDate = Instant.fromEpochMilliseconds(utcTimeMillis)
                    .toLocalDateTime(timeZone).date

                val todayDate = today.toLocalDateTime(timeZone).date
                val maxDate = twoWeeksLater.toLocalDateTime(timeZone).date

                return pickedDate in todayDate..maxDate
            }
        }
    )

    val confirmEnabled = datePickerState.selectedDateMillis != null

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val selectedDate = Instant.fromEpochMilliseconds(millis)
                            .toLocalDateTime(TimeZone.currentSystemDefault())
                            .date
                        onDateSelected(selectedDate.toJavaLocalDate())
                    }
                },
                enabled = confirmEnabled
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Скасувати")
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = Color.White,
            titleContentColor = Color.Black,
            headlineContentColor = Color.DarkGray
        ),
        shape = RoundedCornerShape(24.dp)
    ) {
        DatePicker(state = datePickerState)
    }
}