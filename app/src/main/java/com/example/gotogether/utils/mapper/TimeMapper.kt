package com.example.gotogether.utils.mapper

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object TimeMapper {
    fun toDateWithTimeZone(time: String): LocalDateTime {
        val utcTime = ZonedDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME)
        return utcTime.withZoneSameInstant(ZoneId.of("Europe/Kiev")).toLocalDateTime()
    }
}