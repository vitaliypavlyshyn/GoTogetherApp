package com.example.gotogether.presentation.screens.reviewable_list_trip_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotogether.domain.review.GetReviewsByReviewedUuidUseCase
import com.example.gotogether.domain.review.GetReviewsByReviewerUuidUseCase
import com.example.gotogether.domain.review.PostReviewUseCase
import com.example.gotogether.domain.review.Review
import com.example.gotogether.domain.trip.DetailedTrip
import com.example.gotogether.domain.trip.usecase.GetDetailedTripByIdUseCase
import com.example.gotogether.domain.trip_passenger.TripPassenger
import com.example.gotogether.domain.trip_passenger.usecase.GetPassengersByTripIdUseCase
import com.example.gotogether.domain.user.usecase.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewableListTripViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getDetailedTripByIdUseCase: GetDetailedTripByIdUseCase,
    private val getPassengersByTripIdUseCase: GetPassengersByTripIdUseCase,
    private val getReviewsByReviewerUuidUseCase: GetReviewsByReviewerUuidUseCase,
    private val getReviewsByReviewedUuidUseCase: GetReviewsByReviewedUuidUseCase,
    savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val _state = MutableStateFlow(ReviewableListTripState())
    val state = _state.asStateFlow()
    private val tripId: Long = savedStateHandle.get<String>("tripId")!!.toLong()



    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            val reviewableUsers = getReviewableUsers()

            _state.update {
                it.copy(
                    detailedTrip = getDetailedTrip(),
                    passengers = getPassengers(),
                    reviews = getReviews(),
                    reviewableUsers = reviewableUsers,
                    isLoading = false
                )
            }
        }
    }


    suspend fun getReviewableUsers(): List<UserPreview> {
        val user = getCurrentUserUseCase().getOrNull() ?: return emptyList()
        val trip = getDetailedTripByIdUseCase(tripId).getOrNull() ?: return emptyList()
        val passengers = getPassengersByTripIdUseCase(tripId).getOrNull() ?: return emptyList()
        val existingReviews = getReviewsByReviewerUuidUseCase(user.userUuid).getOrNull() ?: return emptyList()

        // Всі UUID, яким я вже залишив відгуки
        val reviewedUserUuids = existingReviews.map { it.reviewedUserUuid }

        return if (user.userUuid == trip.driverUuid) {
            // Якщо я водій — можу залишати відгуки лише пасажирам, яким ще не писав
            passengers
                .filter { it.passengerUuid !in reviewedUserUuids }
                .map {
                    UserPreview(tripId, it.passengerUuid, it.firstName, it.pictureProfile)
                }
        } else {
            // Якщо я пасажир — можу оцінити лише водія, якщо ще не оцінював
            if (trip.driverUuid !in reviewedUserUuids) {
                listOf(
                    UserPreview(tripId, trip.driverUuid, trip.driverFirstName, trip.driverPicture)
                )
            } else emptyList()
        }
    }

    suspend fun getDetailedTrip() = getDetailedTripByIdUseCase(tripId)
    suspend fun getPassengers() = getPassengersByTripIdUseCase(tripId)
    suspend fun getReviews(): Result<List<Review>>  {
        val user = getCurrentUserUseCase().getOrNull() ?: return Result.failure(Exception("Помилка отримання поточного користувача"))

        return getReviewsByReviewerUuidUseCase(user.userUuid)
    }


    data class ReviewableListTripState(
     //   val user: Result<User>? = null,
        val detailedTrip: Result<DetailedTrip>? = null,
        val passengers: Result<List<TripPassenger>>? = null,
        val reviews: Result<List<Review>>? =null,
        val reviewableUsers: List<UserPreview> = emptyList(),
        val isLoading: Boolean = false,
    )

    data class UserPreview(
        val tripId: Long,
        val uuid: String,
        val name: String,
        val picture: ByteArray? = null
    )
}