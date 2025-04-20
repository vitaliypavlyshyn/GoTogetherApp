package com.example.gotogether.utils.formatter

import kotlinx.datetime.Clock

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Locale

object TimeFormatter {
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
            else -> date.format(formatter) ?: ""
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

    fun formatFullUkrainianDate(dateString: String): String {
        val inputFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val dateTime = LocalDateTime.parse(dateString, inputFormatter)

        val outputFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM", Locale("uk", "UA"))
        return dateTime.format(outputFormatter)
            .replaceFirstChar { it.uppercaseChar() }
    }

    fun getUserAgeFromString(dateOfBirth: String): Int {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
        val birthDate = LocalDate.parse(dateOfBirth, formatter)
        val currentDate = LocalDate.now()

        return Period.between(birthDate, currentDate).years
    }

    fun formatToMonthYear(dateString: String): String {
        return try {
            val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
            val dateTime = OffsetDateTime.parse(dateString, formatter)

            val ukrainianMonths = listOf(
                "січень", "лютий", "березень", "квітень", "травень", "червень",
                "липень", "серпень", "вересень", "жовтень", "листопад", "грудень"
            )

            val month = ukrainianMonths[dateTime.monthValue - 1]
            val year = dateTime.year

            "$month $year"
        } catch (e: Exception) {
            "Невідома дата"
        }
    }
}