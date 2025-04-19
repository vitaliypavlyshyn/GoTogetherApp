package com.example.gotogether.utils.converter

import androidx.compose.runtime.remember
import com.example.gotogether.domain.ChosenRoute
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toLocalDateTime
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object TimeConverter {
    fun toHHmm(timeString: String): String {
        return try {
            val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME // Без часової зони!
            val localDateTime = LocalDateTime.parse(timeString, formatter)
            return localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        } catch (e: Exception) {
            "??:??"
        }
    }

    fun todMMMM(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("d MMMM", Locale("uk", "UA"))
        val timeZone = TimeZone.currentSystemDefault()
        val currentDate = Clock.System.now().toLocalDateTime(timeZone).date

        return when (date) {
            currentDate -> "Сьогодні"
            else -> date.toJavaLocalDate().format(formatter) ?: ""
        }
    }

    fun todMMMM(date: String): String {
        val formatter = DateTimeFormatter.ofPattern("d MMMM", Locale("uk", "UA"))
        val timeZone = TimeZone.currentSystemDefault()
        val currentDate = Clock.System.now().toLocalDateTime(timeZone).date.toString()

        return when (date) {
            currentDate -> "Сьогодні"
            else -> date.format(formatter)
        }
    }
}