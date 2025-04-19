package com.example.gotogether.presentation.screens.trips_list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotogether.domain.ChosenRoute
import com.example.gotogether.domain.location.GetLocationsUseCase
import com.example.gotogether.domain.location.Location
import com.example.gotogether.domain.trip.GetTripsByDateUseCase
import com.example.gotogether.domain.trip.Trip
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripsListViewModel @Inject constructor(
    private val getTripsByDateUseCase: GetTripsByDateUseCase
): ViewModel() {
    private val _state = MutableStateFlow(TripsState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            _state.update {
                it.copy(
                    trips = getTrips(),
                    isLoading = false
                )
            }
        }
    }

    suspend fun getTrips(): Result<List<Trip>>? {
        return getTripsByDateUseCase.invoke(
            ChosenRoute.fromCityId!!,
            ChosenRoute.toCityId!!,
            ChosenRoute.dateTrip.toString())
    }

    data class TripsState(
        val trips: Result<List<Trip>>? = null,
        val isLoading: Boolean = false
    )
}