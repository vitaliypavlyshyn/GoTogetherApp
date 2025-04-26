package com.example.gotogether.presentation.screens.trips_list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotogether.domain.ChosenRoute
import com.example.gotogether.domain.TripStatus
import com.example.gotogether.domain.trip.usecase.GetTripsByDateUseCase
import com.example.gotogether.domain.trip.Trip
import com.example.gotogether.domain.user.User
import com.example.gotogether.domain.user.usecase.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class TripsListViewModel @Inject constructor(
    private val getTripsByDateUseCase: GetTripsByDateUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
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
                    user = getCurrentUser(),
                    trips = getTrips(),
                    isLoading = false
                )
            }
        }
    }

    suspend fun getCurrentUser(): Result<User>? = getCurrentUserUseCase()

    suspend fun getTrips(): Result<List<Trip>>? {
        val now = Instant.now().plus(30, ChronoUnit.MINUTES)
        val zoneId = ZoneId.of("Europe/Kyiv")
        val formatter = DateTimeFormatter.ISO_DATE_TIME

        val userResult = getCurrentUser()
        val user = userResult?.getOrNull()
        if (user == null) return Result.failure(Exception("Користувача не знайдено"))

        val tripsResult = getTripsByDateUseCase(
            ChosenRoute.fromCityId!!,
            ChosenRoute.toCityId!!,
            ChosenRoute.dateTrip.toString()
        )

        val allTrips = tripsResult?.getOrNull() ?: return tripsResult

        val filteredTrips = allTrips
            .mapNotNull { trip ->
                val tripStartTime = try {
                    LocalDateTime.parse(trip.startTime, formatter)
                        .atZone(zoneId)
                        .toInstant()
                } catch (e: Exception) {
                    null
                }

                if (
                    trip.driverUuid != user.userUuid &&
                    trip.status == TripStatus.SCHEDULED.status &&
                    tripStartTime != null &&
                    tripStartTime.isAfter(now)
                ) {
                    trip to tripStartTime
                } else null
            }
            .sortedBy { (_, tripInstant) -> tripInstant }
            .map { (trip, _) -> trip }

        return Result.success(filteredTrips)
    }

    data class TripsState(
        val user: Result<User>? = null,
        val trips: Result<List<Trip>>? = null,
        val isLoading: Boolean = false
    )
}