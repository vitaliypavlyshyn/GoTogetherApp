package com.example.gotogether.presentation.screens.profile_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kotlin.collections.get

@Composable
fun ProfileScreen(
    state: ProfileViewModel.UserState,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.run { align(Alignment.Center) }
            )
        } else {
            val user = state.user
            Column() {
                Text(text = user?.firstName + " " + user?.lastName)
            }
        }
    }
}