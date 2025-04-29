package com.example.gotogether.presentation.screens.my_propositions_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotogether.domain.review.GetReviewsByReviewerUuidUseCase
import com.example.gotogether.domain.trip.Trip
import com.example.gotogether.domain.trip.usecase.GetTripsByDriverUuidUseCase
import com.example.gotogether.domain.trip_passenger.usecase.GetPassengersByTripIdUseCase
import com.example.gotogether.domain.user.usecase.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPropositionsViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getTripsByDriverUuid: GetTripsByDriverUuidUseCase,
    private val getPassengersByTripIdUseCase: GetPassengersByTripIdUseCase,
    private val getReviewsByReviewerUuidUseCase: GetReviewsByReviewerUuidUseCase,
): ViewModel(){
    private val _state = MutableStateFlow(MyPropositionsState())
    val state = _state.asStateFlow()

//    init {
//        viewModelScope.launch {
//            _state.update {
//                it.copy(
//                    isLoading = true
//                )
//            }
//            _state.update {
//                it.copy(
//                    trips = getMyPropositions(),
//                    isLoading = false
//                )
//            }
//        }
//    }

    private suspend fun getReviewableUsersForTrip(tripId: Long, currentUserUuid: String): List<UserPreview> {
        val passengersResult = getPassengersByTripIdUseCase(tripId)
        val reviewsResult = getReviewsByReviewerUuidUseCase(currentUserUuid)

        val passengers = passengersResult.getOrNull().orEmpty()
        val existingReviews = reviewsResult.getOrNull().orEmpty()
        val reviewedUuids = existingReviews.map { it.reviewedUserUuid }

        return passengers
            .filter { it.passengerUuid !in reviewedUuids }
            .map {
                UserPreview(
                    tripId = tripId,
                    uuid = it.passengerUuid,
                    name = it.firstName,
                    picture = it.pictureProfile
                )
            }
    }

    fun loadTrips() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val user = getCurrentUserUseCase().getOrNull()
            if (user == null) {
                _state.update { it.copy(isLoading = false) }
                return@launch
            }

            val tripsResult = getTripsByDriverUuid(user.userUuid)
            val trips = tripsResult.getOrNull().orEmpty()

            val reviewableUsersMap = mutableMapOf<Long, List<UserPreview>>()
            for (trip in trips) {
                if(trip.tripId != null) {
                    val reviewableUsers = getReviewableUsersForTrip(trip.tripId, user.userUuid)
                    reviewableUsersMap[trip.tripId] = reviewableUsers
                    Log.d("myLog", "${reviewableUsersMap[trip.tripId]}")
                }
            }

            _state.update {
                it.copy(
                    trips = tripsResult,
                    reviewableUsersMap = reviewableUsersMap,
                    isLoading = false
                )
            }
        }
    }
    suspend fun getMyPropositions(): Result<List<Trip>>? {
        val userResult = getCurrentUserUseCase()
        val user = userResult.getOrNull()
        if(user != null) {
            return getTripsByDriverUuid(user.userUuid)
        }
        return Result.failure(Exception("Користувача не знайдено"))
    }

    data class MyPropositionsState(
        val trips: Result<List<Trip>>? = null,
        val isLoading: Boolean = false,
        val reviewableUsersMap: Map<Long, List<UserPreview>> = emptyMap()
    )

    data class UserPreview(
        val tripId: Long,
        val uuid: String,
        val name: String,
        val picture: ByteArray? = null
    )
}