package com.example.gotogether.utils.converter

import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

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
}