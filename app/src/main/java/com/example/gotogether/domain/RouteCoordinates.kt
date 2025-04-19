package com.example.gotogether.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class RouteCoordinates(
    val startLat: String,
    val startLng: String,
    val startCity: String,
    val endLat: String,
    val endLng: String,
    val endCity: String
): Parcelable
