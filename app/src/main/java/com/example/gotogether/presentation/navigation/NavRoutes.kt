package com.example.gotogether.presentation.navigation

sealed class NavRoutes(val route: String) {
    object Login : NavRoutes("login")
    object Registration : NavRoutes("registration")
    object Profile : NavRoutes("profile")
    object MyTrips : NavRoutes("my_trips")
    object Proposition : NavRoutes("proposition")
    object SearchTrips : NavRoutes("search_trips")
    object ChooseStartCity : NavRoutes("choose_start_city")
    object ChooseEndCity : NavRoutes("choose_end_city")
    object TripsList : NavRoutes("trips_list")
}