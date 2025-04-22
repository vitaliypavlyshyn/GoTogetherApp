package com.example.gotogether.presentation.screens.google_maps_screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gotogether.config.API_KEY
import com.example.gotogether.data.google_maps_route.fetchRoute
import com.example.gotogether.domain.RouteCoordinates
import com.example.gotogether.ui.theme.Purple
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.MarkerState.Companion.invoke
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

@SuppressLint("UnrememberedMutableState")
@Composable
fun GoogleMapsScreen(
    routeCoordinates: RouteCoordinates,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val startLatLng = LatLng(routeCoordinates.startLat.toDouble(), routeCoordinates.startLng.toDouble())
    val endLatLng = LatLng(routeCoordinates.endLat.toDouble(), routeCoordinates.endLng.toDouble())

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(startLatLng, 10f)
    }

    var polylinePoints by remember { mutableStateOf<List<LatLng>>(emptyList()) }

    LaunchedEffect(Unit) {
        val route = fetchRoute(
            routeCoordinates.startLat,
            routeCoordinates.startLng,
            routeCoordinates.endLat,
            routeCoordinates.endLng)
        polylinePoints = route
    }
    Column {
        IconButton(
            modifier = Modifier.padding(16.dp),
            onClick = {
                navController.popBackStack()
            }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back",
                tint = Purple
            )
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(state = MarkerState(position = startLatLng), title = routeCoordinates.startCity)
            Marker(state = MarkerState(position = endLatLng), title = routeCoordinates.endCity)
            if (polylinePoints.isNotEmpty()) {
                Polyline(
                    points = polylinePoints,
                    color = Color.Blue,
                    width = 5f
                )
            }
        }
    }
}

