package com.example.gotogether.presentation.screens.detailed_trip_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotogether.data.trip.dto.UpdateTripRequest
import com.example.gotogether.data.trip_passenger.dto.CreateTripPassengerRequest
import com.example.gotogether.data.trip_request.dto.CreateTripRequestRequest
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import com.example.gotogether.data.trip_request.dto.UpdateTripRequestRequest
import com.example.gotogether.domain.ChosenRoute
import com.example.gotogether.domain.trip.DetailedTrip
import com.example.gotogether.domain.trip.usecase.GetDetailedTripByIdUseCase
import com.example.gotogether.domain.trip.usecase.PutTripUseCase
import com.example.gotogether.domain.trip_passenger.TripPassenger
import com.example.gotogether.domain.trip_passenger.usecase.DeletePassengerUseCase
import com.example.gotogether.domain.trip_passenger.usecase.GetPassengersByTripIdUseCase
import com.example.gotogether.domain.trip_passenger.usecase.PostPassengerUseCase
import com.example.gotogether.domain.trip_request.TripRequest
import com.example.gotogether.domain.trip_request.TripRequestStatus
import com.example.gotogether.domain.trip_request.usecase.DeleteTripRequestUseCase
import com.example.gotogether.domain.trip_request.usecase.GetTripRequestByTripIdAndUserUuidUseCase
import com.example.gotogether.domain.trip_request.usecase.PostTripRequestUseCase
import com.example.gotogether.domain.trip_request.usecase.PutTripRequestUseCase
import com.example.gotogether.domain.user.usecase.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.firstOrNull

@HiltViewModel
class DetailedTripViewModel @Inject constructor(
    private val getDetailedTripByIdUseCase: GetDetailedTripByIdUseCase,
    private val getPassengersByTripIdUseCase: GetPassengersByTripIdUseCase,
    private val getTripRequestByTripIdAndUserUuidUseCase: GetTripRequestByTripIdAndUserUuidUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val postPassengerUseCase: PostPassengerUseCase,
    private val postTripRequestUseCase: PostTripRequestUseCase,
    private val deleteTripRequestUseCase: DeleteTripRequestUseCase,
    private val putTripUseCase: PutTripUseCase,
    private val deleteTripPassengerUseCase: DeletePassengerUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _state = MutableStateFlow(DetailedTripState())
    val state = _state.asStateFlow()

    var insertPassengerResult by mutableStateOf<Result<ResponseDTO>?>(null)
        private set
    var insertRequestResult by mutableStateOf<Result<ResponseDTO>?>(null)
        private set
    var deletePassengerResult by mutableStateOf<Result<ResponseDTO>?>(null)
        private set
    var deleteRequestResult by mutableStateOf<Result<ResponseDTO>?>(null)
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
                    myRequest = getMyRequest(),
                    isPassengerIsMe = checkIsPassengerIsMe(),
                    isLoading = false
                )
            }
        }
    }

    fun deleteRequest() {
        viewModelScope.launch {
            try {
                val userResult = getCurrentUserUseCase()
                val user = userResult.getOrNull()
                if (user != null) {
                    val requestResult =
                        getTripRequestByTripIdAndUserUuidUseCase(tripId, user.userUuid)
                    val requestId = requestResult.getOrNull()?.requestId
                    if (requestId != null) {
                        deleteRequestResult = deleteTripRequestUseCase(requestId)
                    }
                }
            } catch (e: Exception) {
                deleteRequestResult = Result.failure(e)
            }
        }
    }

    fun deletePassenger() {
        viewModelScope.launch {
            val user = getCurrentUserUseCase().getOrNull()
            val currentTrip = getDetailedTripByIdUseCase(tripId).getOrNull()
            val passengers = getPassengersByTripIdUseCase(tripId).getOrNull()

            if (user != null) {
                if (currentTrip != null && passengers != null) {

                    val passengerIsMe = passengers.firstOrNull { it.passengerUuid == user.userUuid }
                    if (passengerIsMe != null) {
                        val availableSeats = currentTrip.availableSeats + passengerIsMe.seatsBooked
                        deletePassengerResult = deleteTripPassengerUseCase(
                            tripId, passengerIsMe.passengerUuid
                        )
                        putTripUseCase(
                            tripId, UpdateTripRequest(
                                availableSeats = availableSeats
                            )
                        )
                    }
                }
            }
        }
    }

    suspend fun checkIsPassengerIsMe(): Boolean {
        val userResult = getCurrentUserUseCase()
        val user = userResult.getOrNull()

        val passengersResult = getPassengersByTripIdUseCase(tripId)
        val passengers = passengersResult.getOrNull()

        return if (user != null && passengers != null) {
            passengers.any { it.passengerUuid == user.userUuid }
        } else {
            false
        }
    }

    fun bookTrip() {
        viewModelScope.launch {
            val user = getCurrentUserUseCase().getOrNull()
            val currentTrip = getDetailedTripByIdUseCase(tripId).getOrNull()
            val passengers = getPassengersByTripIdUseCase(tripId).getOrNull()

            if (user != null) {
                if (currentTrip != null && passengers != null) {
                    insertPassengerResult = postPassengerUseCase(
                        request = CreateTripPassengerRequest(
                            tripId = tripId,
                            passengerUuid = user.userUuid,
                            seatsBooked = ChosenRoute.seatsCount
                        )
                    )
                    putTripUseCase(
                        tripId,
                        UpdateTripRequest(
                            availableSeats = currentTrip.availableSeats - ChosenRoute.seatsCount
                        )
                    )
                }
            }
        }
    }

    fun createTripRequest() {
        viewModelScope.launch {
            val userResult = getCurrentUserUseCase()
            val user = userResult.getOrNull()

            if (user != null) {
                insertRequestResult = postTripRequestUseCase(
                    request = CreateTripRequestRequest(
                        tripId = tripId,
                        passengerUuid = user.userUuid,
                        requestedSeats = ChosenRoute.seatsCount,
                    )
                )
            }
        }
    }

    suspend fun getDetailedTrip() = getDetailedTripByIdUseCase(tripId)
    suspend fun getPassengers() = getPassengersByTripIdUseCase(tripId)
    suspend fun getMyRequest(): Result<TripRequest>? {
        val userResult = getCurrentUserUseCase()
        val user = userResult.getOrNull() ?: return null
        return getTripRequestByTripIdAndUserUuidUseCase(tripId, user.userUuid)
    }

    data class DetailedTripState(
        val detailedTrip: Result<DetailedTrip>? = null,
        val passengers: Result<List<TripPassenger>>? = null,
        val myRequest: Result<TripRequest>? = null,
        val isPassengerIsMe: Boolean? = null,
        val isLoading: Boolean = false,
    )
}