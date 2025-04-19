package com.example.gotogether.domain.location

data class Location(
    val cityId: Long,
    val regionId: Long,
    val cityNameUk: String,
    val lat: String,
    val lng: String,
    val adminNameUk: String
)
