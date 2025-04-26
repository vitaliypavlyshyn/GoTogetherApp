package com.example.gotogether.data.google_maps_route

data class DirectionsResponseDTO(
    val routes: List<Route>
)

data class Route(
    val legs: List<Leg>
)

data class Leg(
    val distance: ValueText,
    val duration: ValueText
)

data class ValueText(
    val text: String,
    val value: Int
)