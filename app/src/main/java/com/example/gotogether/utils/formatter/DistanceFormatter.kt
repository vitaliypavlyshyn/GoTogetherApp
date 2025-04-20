package com.example.gotogether.utils.formatter

import java.util.Locale

object DistanceFormatter {
    fun formatDistanceInKm(distanceInMeters: Int): Double {
        return (distanceInMeters / 1000.0).let {
            String.format(Locale.US, "%.2f", it).toDouble()
        }
    }
}