package com.example.gotogether.presentation.screens.user_profile_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotogether.domain.trip.DetailedTrip
import com.example.gotogether.domain.trip_passenger.TripPassenger
import com.example.gotogether.domain.user.User
import com.example.gotogether.domain.user.usecase.GetUserByUuidUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val getUserByUuidUseCase: GetUserByUuidUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _state = MutableStateFlow(UserState())
    val state = _state.asStateFlow()

    private val userUuid: String = savedStateHandle.get<String>("userUuid")!!

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            _state.update {
                it.copy(
                    user = getUser(userUuid),
                    isLoading = false
                )
            }
        }
    }

    suspend fun getUser(userUuid: String): Result<User> = getUserByUuidUseCase(userUuid)

    data class UserState(
        val user: Result<User>? = null,
        val isLoading: Boolean = false,
    )
}