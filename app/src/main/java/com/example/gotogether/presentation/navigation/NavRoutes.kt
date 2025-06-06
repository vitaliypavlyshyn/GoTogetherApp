package com.example.gotogether.presentation.navigation

sealed class NavRoutes(val route: String) {
    object Login : NavRoutes("login")
    object Registration : NavRoutes("registration")
    object Profile : NavRoutes("profile")
    object MyTrips : NavRoutes("my_trips")
    object Proposition : NavRoutes("proposition")
    object MyPropositions : NavRoutes("my_propositions")
    object SearchTrips : NavRoutes("search_trips")
    object ChooseStartCity : NavRoutes("choose_start_city")
    object ChooseEndCity : NavRoutes("choose_end_city")
    object ChooseCar : NavRoutes("choose_car")
    object TripsList : NavRoutes("trips_list")
    object DetailedTrip : NavRoutes("detailed_trip")
    object GoogleMaps : NavRoutes("google_maps")
    object UserProfile : NavRoutes("user_profile")
    object Reviews : NavRoutes("reviews")
    object ChangeInfo : NavRoutes("change_info")
    object Settings : NavRoutes("settings")
    object DetailedProposition : NavRoutes("detailed_proposition")
    object ReviewableListTrip : NavRoutes("reviewable_list_trip")
    object WriteReview : NavRoutes("write_review")
}