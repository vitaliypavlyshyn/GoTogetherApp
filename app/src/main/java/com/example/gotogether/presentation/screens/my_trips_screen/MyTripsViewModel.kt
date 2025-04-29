package com.example.gotogether.presentation.screens.my_trips_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotogether.domain.TripStatus
import com.example.gotogether.domain.review.GetReviewsByReviewerUuidUseCase
import com.example.gotogether.domain.trip.Trip
import com.example.gotogether.domain.trip.usecase.GetTripsByPassengerUuidUseCase
import com.example.gotogether.domain.trip.usecase.GetTripsByRequesterUuidUseCase
import com.example.gotogether.domain.trip_request.usecase.GetTripRequestByTripIdAndUserUuidUseCase
import com.example.gotogether.domain.user.usecase.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.map
import kotlin.collections.orEmpty

@HiltViewModel
class MyTripsViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getTripsByPassengerUuid: GetTripsByPassengerUuidUseCase,
    private val getTripsByRequesterUuid: GetTripsByRequesterUuidUseCase,
    private val getReviewsByReviewerUuidUseCase: GetReviewsByReviewerUuidUseCase,
    private val getTripRequestByTripIdAndUserUuidUseCase: GetTripRequestByTripIdAndUserUuidUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(MyTripsState())
    val state = _state.asStateFlow()

    fun loadTrips() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val user = getCurrentUserUseCase().getOrNull()
            if (user == null) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        bookedTrips = Result.failure(Exception("Користувача не знайдено")),
                        requests = Result.failure(Exception("Користувача не знайдено"))
                    )
                }
                return@launch
            }

            // Отримуємо всі відгуки про водіїв
            val reviewedDrivers = getReviewsByReviewerUuidUseCase(user.userUuid)
                .getOrNull()
                .orEmpty()
                .map { it.reviewedUserUuid }

            // Отримуємо заброньовані поїздки
            val bookedTripsResult = getTripsByPassengerUuid(user.userUuid)
            val bookedTrips = bookedTripsResult.getOrNull().orEmpty()

            val transformedBooked = bookedTrips.map { trip ->
                TripPreviewWithDriverReviewStatus(
                    trip = trip,
                    canLeaveReviewForDriver = trip.driverUuid !in reviewedDrivers && trip.status == TripStatus.COMPLETED.status,
                    wasPassenger = true,
                    requestStatus = null // для заброньованих поїздок немає статусу запиту
                )
            }

            // Отримуємо запити
            val requestsResult = getTripsByRequesterUuid(user.userUuid)
            val requestedTrips = requestsResult.getOrNull().orEmpty()

            val transformedRequests = requestedTrips.map { trip ->
                // Отримуємо статус запиту для кожного запиту
                val requestStatus = try {
                    getTripRequestByTripIdAndUserUuidUseCase(trip.tripId!!, user.userUuid).getOrNull()?.status
                } catch (e: Exception) {
                    null // обробка можливих помилок
                }

                TripPreviewWithDriverReviewStatus(
                    trip = trip,
                    canLeaveReviewForDriver = false, // Запити не мають можливості залишити відгук
                    wasPassenger = false,
                    requestStatus = requestStatus
                )
            }

            // Оновлюємо стан
            _state.update {
                it.copy(
                    bookedTrips = Result.success(transformedBooked),
                    requests = Result.success(transformedRequests),
                    isLoading = false
                )
            }
        }
    }

    data class MyTripsState(
        val bookedTrips: Result<List<TripPreviewWithDriverReviewStatus>>? = null,
        val requests: Result<List<TripPreviewWithDriverReviewStatus>>? = null,
        val isLoading: Boolean = false
    )

    data class TripPreviewWithDriverReviewStatus(
        val trip: Trip,
        val canLeaveReviewForDriver: Boolean,
        val wasPassenger: Boolean,
        val requestStatus: String? = null
    )
}