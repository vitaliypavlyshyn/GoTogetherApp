package com.example.gotogether.data.location

import com.example.gotogether.domain.location.Location

data class LocationResponse(
    val cityId: Long,
    val regionId: Long,
    val cityName: String,
    val cityNameUk: String,
    val lat: String,
    val lng: String,
    val country: String,
    val iso2: String,
    val adminName: String,
    val adminNameUk: String
)

fun LocationResponse.toDomain(): Location {
    return Location(
        cityId = cityId,
        regionId = regionId,
        cityNameUk = cityNameUk,
        lat = lat,
        lng = lng,
        adminNameUk = adminNameUk
    )
}

fun List<LocationResponse>.toDomainList(): List<Location> {
    val locations = mutableListOf<Location>()
    for (locationDTO in this) {
        locations.add(locationDTO.toDomain())
    }
    return locations
}