package com.example.gotogether.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.Duration

object ChosenRoute {
    var fromCityId: Long? = null
    var fromCityName: String? = null
    var fromCityAdminName: String? = null
    var startLat: String? = null
    var startLng: String? = null

    var toCityId: Long? = null
    var toCityName: String? = null
    var toCityAdminName: String? = null
    var endLat: String? = null
    var endLng: String? = null

    var hourTrip: String? = null
    var isFastConfirm = true

    var seatsCount: Int = 1
    var dateTrip: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    var price: Int? = null


    fun createInstantFromChosenRoute(): String? {
        val date = dateTrip
        val hourTrip = hourTrip

        if (hourTrip.isNullOrEmpty()) return null

        val parts = hourTrip.split(":")
        if (parts.size != 2) return null

        val hour = parts[0].toIntOrNull() ?: return null
        val minute = parts[1].toIntOrNull() ?: return null

        val localDateTime = LocalDateTime(
            year = date.year,
            monthNumber = date.monthNumber,
            dayOfMonth = date.dayOfMonth,
            hour = hour,
            minute = minute,
            second = 0,
            nanosecond = 0
        )

        val shiftedDateTime = localDateTime.toJavaLocalDateTime().minus(Duration.ofHours(3))

        return shiftedDateTime.toKotlinLocalDateTime().toInstant(TimeZone.UTC).toString()
    }
}