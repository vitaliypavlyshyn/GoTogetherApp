package com.example.gotogether.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gotogether.presentation.screens.login_screen.LoginScreen
import com.example.gotogether.presentation.screens.login_screen.LoginViewModel
import com.example.gotogether.presentation.screens.my_trips_screen.MyTripsScreen
import com.example.gotogether.presentation.screens.profile_screen.ProfileScreen
import com.example.gotogether.presentation.screens.profile_screen.ProfileViewModel
import com.example.gotogether.presentation.screens.proposition_screens.PropositionScreen
import com.example.gotogether.presentation.screens.registration_screen.RegistrationScreen
import com.example.gotogether.presentation.screens.search_trips_screen.SearchTripsScreen

@Composable
fun NavigationController(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Login.route,
        modifier = modifier
    ) {
        composable(NavRoutes.Profile.route) {
            val profileViewModel = hiltViewModel<ProfileViewModel>()
            val profileState = profileViewModel.state.collectAsState()
            ProfileScreen(
                profileState = profileState.value,
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
            SearchTripsScreen()
        }
        composable(NavRoutes.Login.route) {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(
                loginViewModel = loginViewModel,
                navController = navController
            )
        }
        composable(NavRoutes.Registration.route) {
            RegistrationScreen(
                navController = navController
            )
        }
    }
}