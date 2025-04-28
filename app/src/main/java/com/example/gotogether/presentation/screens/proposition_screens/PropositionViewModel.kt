package com.example.gotogether.presentation.screens.proposition_screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotogether.data.google_maps_route.getDirectionResponse
import com.example.gotogether.data.trip.dto.CreateTripRequest
import com.example.gotogether.data.trip.dto.CreateTripResponse
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import com.example.gotogether.domain.ChosenRoute
import com.example.gotogether.domain.trip.CreateTrip
import com.example.gotogether.domain.trip.usecase.PostTripUseCase
import com.example.gotogether.domain.user.User
import com.example.gotogether.domain.user.usecase.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.toInstant
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class PropositionViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val postTripUseCase: PostTripUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(PropositionState())
    val state = _state.asStateFlow()

    var insertResult by mutableStateOf<Result<ResponseDTO>?>(null)
        private set

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
                    isLoading = false
                )
            }
        }
    }

    suspend fun getCurrentUser(): Result<User>? = getCurrentUserUseCase()

    fun refreshUser() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            _state.update {
                it.copy(
                    user = getCurrentUser(),
                    isLoading = false
                )
            }
        }
    }

    fun createTrip(driverUUid: String) {
        viewModelScope.launch {
            val directionResponseDTO = getDirectionResponse(
                startLat = ChosenRoute.startLat.toString(),
                startLng = ChosenRoute.startLng.toString(),
                endLat = ChosenRoute.endLat.toString(),
                endLng = ChosenRoute.endLng.toString()
            )
            val tripStartTime = ChosenRoute.createInstantFromChosenRoute()?.toInstant()
            if (directionResponseDTO != null && ChosenRoute.fromCityId != null
                && ChosenRoute.toCityId != null && ChosenRoute.price != null) {
                val requestDTO = CreateTripRequest(
                    driverUuid = driverUUid,
                    startLocationId = ChosenRoute.fromCityId ?: -1,
                    endLocationId = ChosenRoute.toCityId ?: -1,
                    startTime = tripStartTime.toString(),
                    endTime = tripStartTime?.plus(directionResponseDTO.second.seconds).toString(),
                    distanceInMeters = directionResponseDTO.first,
                    availableSeats = ChosenRoute.seatsCount,
                    price = ChosenRoute.price ?: -1,
                    isFastConfirm = ChosenRoute.isFastConfirm
                )

                insertResult = postTripUseCase(requestDTO)
            }
        }
    }

    data class PropositionState(
        val user: Result<User>? = null,
        val isLoading: Boolean = false,
    )
}