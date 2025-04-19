package com.example.gotogether.presentation.screens.detailed_trip_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotogether.domain.trip.DetailedTrip
import com.example.gotogether.domain.trip.GetDetailedTripByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Optional
import javax.inject.Inject
import kotlin.text.toIntOrNull

@HiltViewModel
class DetailedTripVewModel @Inject constructor(
    private val getDetailedTripByIdUseCase: GetDetailedTripByIdUseCase,
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
                    isLoading = false
                )
            }
        }
    }

    suspend fun getDetailedTrip(tripId: Long) = getDetailedTripByIdUseCase(tripId)

    data class DetailedTripState(
        val detailedTrip: Result<DetailedTrip>? = null,
        val isLoading: Boolean = false,
    )
}