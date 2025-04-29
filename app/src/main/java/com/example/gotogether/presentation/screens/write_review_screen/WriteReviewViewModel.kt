package com.example.gotogether.presentation.screens.write_review_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotogether.data.review.CreateReviewRequest
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import com.example.gotogether.domain.review.PostReviewUseCase
import com.example.gotogether.domain.trip.DetailedTrip
import com.example.gotogether.domain.trip.usecase.GetDetailedTripByIdUseCase
import com.example.gotogether.domain.user.User
import com.example.gotogether.domain.user.usecase.GetCurrentUserUseCase
import com.example.gotogether.domain.user.usecase.GetUserByUuidUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteReviewViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getDetailedTripByIdUseCase: GetDetailedTripByIdUseCase,
    private val getUserByUuidUseCase: GetUserByUuidUseCase,
    private val postReviewUseCase: PostReviewUseCase,
    savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val _state = MutableStateFlow(WriteReviewState())
    val state = _state.asStateFlow()
    private val tripId: Long = savedStateHandle.get<String>("tripId")?.toLongOrNull()
        ?: throw IllegalArgumentException("tripId is missing or invalid")
    private val userUuid: String = savedStateHandle.get<String>("userUuid")
        ?: throw IllegalArgumentException("userUuid is missing")

    var insertReviewResult by mutableStateOf<Result<ResponseDTO>?>(null)
        private set

    init {
        viewModelScope.launch {
            Log.d("myLog", "${tripId}, ${userUuid}")
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            _state.update {
                it.copy(
                    currentUser = getCurrentUser(),
                    detailedTrip = getDetailedTrip(),
                    reviewedUser = getReviewableUser(),
                    isLoading = false
                )
            }
        }
    }

    fun isReviewedUserDriver(): Boolean {
        val trip = _state.value.detailedTrip?.getOrNull()
        return trip?.driverUuid == userUuid
    }

    fun createReview(currentUserUuid: String, rating: Int, drivingSkills: Int?, comment: String) {
        viewModelScope.launch {
            Log.d("WriteReview", "Sending review: $rating, $drivingSkills, $comment")
                insertReviewResult = postReviewUseCase(
                    CreateReviewRequest(
                        tripId = tripId,
                        reviewerUuid = currentUserUuid,
                        reviewedUserUuid = userUuid,
                        rating = rating,
                        drivingSkills = drivingSkills,
                        comment = comment
                    )
                )
        }
    }

    suspend fun getReviewableUser() = getUserByUuidUseCase(userUuid)
    suspend fun getCurrentUser() = getCurrentUserUseCase()
    suspend fun getDetailedTrip() = getDetailedTripByIdUseCase(tripId)

    data class WriteReviewState(
        val currentUser: Result<User>? = null,
        val reviewedUser: Result<User>? = null,
        val detailedTrip: Result<DetailedTrip>? = null,
        val isLoading: Boolean = false,
    )
}