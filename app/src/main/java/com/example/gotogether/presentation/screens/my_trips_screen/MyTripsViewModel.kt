package com.example.gotogether.presentation.screens.my_trips_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotogether.domain.trip.Trip
import com.example.gotogether.domain.trip.usecase.GetTripsByDriverUuid
import com.example.gotogether.domain.trip.usecase.GetTripsByPassengerUuid
import com.example.gotogether.domain.trip.usecase.GetTripsByRequesterUuid
import com.example.gotogether.domain.user.usecase.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyTripsViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getTripsByPassengerUuid: GetTripsByPassengerUuid,
    private val getTripsByRequesterUuid: GetTripsByRequesterUuid
): ViewModel() {
    private val _state = MutableStateFlow(MyTripsState())
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
                    bookedTrips = getMyBookedTrips(),
                    requests = getMyRequests(),
                    isLoading = false
                )
            }
        }
    }

    fun loadTrips() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val resultBookedTrips = getMyBookedTrips()
            val resultRequests = getMyRequests()
            _state.update {
                it.copy(
                    bookedTrips = resultBookedTrips,
                    requests = resultRequests,
                    isLoading = false
                )
            }
        }
    }
    suspend fun getMyBookedTrips(): Result<List<Trip>>? {
        val userResult = getCurrentUserUseCase()
        val user = userResult.getOrNull()
        if(user != null) {
            return getTripsByPassengerUuid(user.userUuid)
        }
        return Result.failure(Exception("Користувача не знайдено"))
    }

    suspend fun getMyRequests(): Result<List<Trip>>? {
        val userResult = getCurrentUserUseCase()
        val user = userResult.getOrNull()
        if(user != null) {
            return getTripsByRequesterUuid(user.userUuid)
        }
        return Result.failure(Exception("Користувача не знайдено"))
    }

    data class MyTripsState(
        val bookedTrips: Result<List<Trip>>? = null,
        val requests: Result<List<Trip>>? = null,
        val isLoading: Boolean = false
    )
}