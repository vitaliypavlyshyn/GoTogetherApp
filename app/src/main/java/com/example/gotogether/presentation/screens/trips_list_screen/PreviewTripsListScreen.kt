package com.example.gotogether.presentation.screens.trips_list_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gotogether.presentation.screens.trips_list_screen.components.TripCardPreview

@Preview(showBackground = true)
@Composable
fun PreviewTripsListScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = Modifier.background(Color.White).fillMaxSize().padding(16.dp)
    ) {
        items(15) {
            TripCardPreview()
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}