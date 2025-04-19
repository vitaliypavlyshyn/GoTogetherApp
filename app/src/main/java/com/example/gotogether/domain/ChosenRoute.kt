package com.example.gotogether.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object ChosenRoute {
    var fromCityId: Long? = 64//null
    var fromCityName: String? = null
    var fromCityAdminName: String? = null

    var toCityId: Long? = 6//null
    var toCityName: String? = null
    var toCityAdminName: String? = null

    var seatsCount: Int = 1
    var dateTrip: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
}