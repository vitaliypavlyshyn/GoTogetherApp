package com.example.gotogether.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gotogether.domain.RouteCoordinates
import com.example.gotogether.presentation.screens.detailed_trip_screen.DetailedTripScreen
import com.example.gotogether.presentation.screens.detailed_trip_screen.DetailedTripVewModel
import com.example.gotogether.presentation.screens.google_maps_screen.GoogleMapsScreen
import com.example.gotogether.presentation.screens.login_screen.LoginScreen
import com.example.gotogether.presentation.screens.my_trips_screen.MyTripsScreen
import com.example.gotogether.presentation.screens.profile_screen.ProfileScreen
import com.example.gotogether.presentation.screens.proposition_screens.PropositionScreen
import com.example.gotogether.presentation.screens.registration_screen.RegistrationScreen
import com.example.gotogether.presentation.screens.reviews_screen.ReviewsScreen
import com.example.gotogether.presentation.screens.reviews_screen.ReviewsViewModel
import com.example.gotogether.presentation.screens.search_trips_screen.SearchTripsScreen
import com.example.gotogether.presentation.screens.settings_screen.SettingsScreen
import com.example.gotogether.presentation.screens.trips_list_screen.TripsListScreen
import com.example.gotogether.presentation.screens.trips_list_screen.TripsListViewModel
import com.example.gotogether.presentation.screens.user_profile_screen.UserProfileScreen
import com.example.gotogether.presentation.screens.user_profile_screen.UserProfileViewModel
import com.example.gotogether.presentation.screens.validation_screens.change_info_screen.ChangeInfoScreen
import com.example.gotogether.presentation.screens.validation_screens.choose_car_screen.ChooseCarScreen
import com.example.gotogether.presentation.screens.validation_screens.choose_cities_screens.ChooseEndCityScreen
import com.example.gotogether.presentation.screens.validation_screens.choose_cities_screens.ChooseStartCityScreen
import com.example.gotogether.presentation.screens.validation_screens.choose_cities_screens.LocationViewModel

@Composable
fun NavigationController(
    navController: NavHostController,
    modifier: Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Login.route,
        modifier = modifier
    ) {
        composable(NavRoutes.Login.route) {
            LoginScreen(
                navController = navController
            )
        }
        composable(NavRoutes.Registration.route) {
            RegistrationScreen(
                navController = navController
            )
        }
        composable(NavRoutes.Profile.route) {
            ProfileScreen(
                navController = navController
            )
        }
        composable(NavRoutes.MyTrips.route) { stackEntry ->
            MyTripsScreen(
                modifier = Modifier.padding(top = 100.dp)
            )
        }
        composable(NavRoutes.Proposition.route) {
            PropositionScreen()
        }
        composable(NavRoutes.SearchTrips.route) {
            SearchTripsScreen(
                navController = navController
            )
        }
        composable(NavRoutes.Registration.route) {
            RegistrationScreen(
                navController = navController
            )
        }
        composable(NavRoutes.ChooseStartCity.route) {
            val chooseLocationViewModel = hiltViewModel<LocationViewModel>()
            val locationState = chooseLocationViewModel.state.collectAsState()
            ChooseStartCityScreen(
                locationState = locationState.value,
                navController = navController
            )
        }
        composable(NavRoutes.ChooseEndCity.route) {
            val chooseLocationViewModel = hiltViewModel<LocationViewModel>()
            val locationState = chooseLocationViewModel.state.collectAsState()
            ChooseEndCityScreen(
                locationState = locationState.value,
                navController = navController
            )
        }
        composable(NavRoutes.ChooseCar.route) {
            ChooseCarScreen(
                navController = navController
            )
        }
        composable(NavRoutes.TripsList.route) {
            val tripsListViewModel = hiltViewModel<TripsListViewModel>()
            val tripsListState = tripsListViewModel.state.collectAsState()
            TripsListScreen(
                tripsListState = tripsListState.value,
                navController = navController
            )
        }
        composable(NavRoutes.DetailedTrip.route + "/{tripId}") {
            val detailedTripViewModel = hiltViewModel<DetailedTripVewModel>()
            val detailedTripState = detailedTripViewModel.state.collectAsState()
            DetailedTripScreen(
                detailedTripState = detailedTripState.value,
                navController = navController
            )
        }
        composable(NavRoutes.GoogleMaps.route) {
            val routeCoordinates = remember {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<RouteCoordinates>("coordinates")
            }

            if (routeCoordinates != null) {
                GoogleMapsScreen(
                    routeCoordinates = routeCoordinates,
                    navController = navController
                )
            }
        }
        composable(NavRoutes.UserProfile.route + "/{userUuid}") {
            val userProfileViewModel = hiltViewModel<UserProfileViewModel>()
            val userState = userProfileViewModel.state.collectAsState()
            UserProfileScreen(
                userState = userState.value,
                navController = navController
            )
        }
        composable(NavRoutes.Reviews.route + "/{userUuid}") {
            val reviewsViewModel = hiltViewModel<ReviewsViewModel>()
            val reviewsState = reviewsViewModel.state.collectAsState()
            ReviewsScreen(
                reviewsState = reviewsState.value,
                navController = navController,
            )
        }
        composable(NavRoutes.ChangeInfo.route) {
            ChangeInfoScreen(
                navController = navController,
            )
        }
        composable(NavRoutes.Settings.route + "/{userUuid}") {
            SettingsScreen(
                navController = navController,
            )
        }
    }
}