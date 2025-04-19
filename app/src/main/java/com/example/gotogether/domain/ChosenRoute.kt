package com.example.gotogether.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object ChosenRoute {
    var fromCityId: Long? = null
    var fromCityName: String? = null
    var fromCityAdminName: String? = null

    var toCityId: Long? = null
    var toCityName: String? = null
    var toCityAdminName: String? = null

    var seatsCount: Int = 1
    var dateTrip: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
}