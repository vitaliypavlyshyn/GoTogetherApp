package com.example.gotogether.presentation.screens.detailed_trip_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotogether.domain.trip.DetailedTrip
import com.example.gotogether.domain.trip.usecase.GetDetailedTripByIdUseCase
import com.example.gotogether.domain.trip_passenger.GetPassengersByTripIdUseCase
import com.example.gotogether.domain.trip_passenger.TripPassenger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailedTripVewModel @Inject constructor(
    private val getDetailedTripByIdUseCase: GetDetailedTripByIdUseCase,
    private val getPassengersByTripIdUseCase: GetPassengersByTripIdUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _state = MutableStateFlow(DetailedTripState())
    val state = _state.asStateFlow()

    private val tripId: Long = savedStateHandle.get<String>("tripId")!!.toLong()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            _state.update {
                it.copy(
                    detailedTrip = getDetailedTrip(tripId),
                    passengers = getPassengers(tripId),
                    isLoading = false
                )
            }
        }
    }

    suspend fun getDetailedTrip(tripId: Long) = getDetailedTripByIdUseCase(tripId)
    suspend fun getPassengers(tripId: Long) = getPassengersByTripIdUseCase(tripId)

    data class DetailedTripState(
        val detailedTrip: Result<DetailedTrip>? = null,
        val passengers: Result<List<TripPassenger>>? = null,
        val isLoading: Boolean = false,
    )
}