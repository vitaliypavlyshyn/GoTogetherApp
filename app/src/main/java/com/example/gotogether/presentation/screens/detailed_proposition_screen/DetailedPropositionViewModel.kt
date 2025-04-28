package com.example.gotogether.presentation.screens.detailed_proposition_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotogether.data.trip.dto.UpdateTripRequest
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import com.example.gotogether.data.trip_request.dto.UpdateTripRequestRequest
import com.example.gotogether.domain.TripStatus
import com.example.gotogether.domain.trip.DetailedTrip
import com.example.gotogether.domain.trip.usecase.GetDetailedTripByIdUseCase
import com.example.gotogether.domain.trip.usecase.PutTripUseCase
import com.example.gotogether.domain.trip_passenger.TripPassenger
import com.example.gotogether.domain.trip_passenger.usecase.GetPassengersByTripIdUseCase
import com.example.gotogether.domain.trip_request.TripRequest
import com.example.gotogether.domain.trip_request.TripRequestStatus
import com.example.gotogether.domain.trip_request.usecase.GetTripRequestsByTripIdUseCase
import com.example.gotogether.domain.trip_request.usecase.PutTripRequestUseCase
import com.example.gotogether.domain.user.usecase.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailedPropositionViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val putTripUseCase: PutTripUseCase,
    private val getDetailedTripByIdUseCase: GetDetailedTripByIdUseCase,
    private val getPassengersByTripIdUseCase: GetPassengersByTripIdUseCase,
    private val getTripRequestsByTripIdUseCase: GetTripRequestsByTripIdUseCase,
    private val putTripRequestUseCase: PutTripRequestUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _state = MutableStateFlow(DetailedPropositionState())
    val state = _state.asStateFlow()

    var updateTripResult by mutableStateOf<Result<ResponseDTO>?>(null)
        private set
    var updateTripRequestResult by mutableStateOf<Result<ResponseDTO>?>(null)
        private set

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
                    detailedTrip = getDetailedTrip(),
                    passengers = getPassengers(),
                    tripRequests = getTripRequests(),
                    isLoading = false
                )
            }
        }
    }

    fun refreshTripRequests() {
        viewModelScope.launch {
            _state.update { currentState ->
                currentState.copy(
                    tripRequests = getTripRequests(),
                    passengers = getPassengers()
                )
            }
        }
    }

    fun cancelTrip() {
        viewModelScope.launch {
            updateTripResult = putTripUseCase(
                tripId, UpdateTripRequest(status = TripStatus.CANCELED.status)
            )

        }
    }

    fun manageRequest(requestId: Long, requestStatus: TripRequestStatus) {
        viewModelScope.launch {
            updateTripRequestResult = putTripRequestUseCase(
                requestId, UpdateTripRequestRequest(status = requestStatus.status)
            )

            if (requestStatus == TripRequestStatus.ACCEPTED) {
                val currentTrip = getDetailedTripByIdUseCase(tripId).getOrNull()
                val tripRequests = getTripRequestsByTripIdUseCase(tripId).getOrNull()

                if (currentTrip != null && tripRequests != null) {
                    val request = tripRequests.firstOrNull { it.requestId == requestId }
                    if (request != null) {
                        val availableSeats = currentTrip.availableSeats - request.requestedSeats
                        putTripUseCase(
                            tripId,
                            UpdateTripRequest(
                                availableSeats = availableSeats
                            )
                        )
                    }
                }
            }

            refreshTripRequests()
        }
    }
    suspend fun getDetailedTrip() = getDetailedTripByIdUseCase(tripId)
    suspend fun getPassengers() = getPassengersByTripIdUseCase(tripId)
    suspend fun getTripRequests(): Result<List<TripRequest>> {
        val tripResult = getDetailedTripByIdUseCase(tripId)
        val tripRequestsResult = getTripRequestsByTripIdUseCase(tripId)

        if (tripResult.isFailure || tripRequestsResult.isFailure) {
            return Result.failure(Exception("Не вдалося отримати дані"))
        }

        val availableSeats = tripResult.getOrNull()?.availableSeats ?: return Result.failure(Exception("Немає доступних місць"))

        val filteredRequests = tripRequestsResult.getOrNull()
            ?.filter { it.requestedSeats <= availableSeats }
            ?: emptyList()

        return Result.success(filteredRequests)
    }

    data class DetailedPropositionState(
        val detailedTrip: Result<DetailedTrip>? = null,
        val passengers: Result<List<TripPassenger>>? = null,
        val tripRequests: Result<List<TripRequest>>? = null,
        val isLoading: Boolean = false,
    )
}