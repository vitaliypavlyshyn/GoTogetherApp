package com.example.gotogether.utils.extentions

import kotlin.math.roundToInt

fun Double.roundTo2DecimalPlaces(): Double {
    return (this * 100).roundToInt() / 100.0
}