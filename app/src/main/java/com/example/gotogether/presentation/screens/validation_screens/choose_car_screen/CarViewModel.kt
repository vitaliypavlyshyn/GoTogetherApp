package com.example.gotogether.presentation.screens.validation_screens.choose_car_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import com.example.gotogether.data.user.UpdateUserRequest
import com.example.gotogether.domain.car.Car
import com.example.gotogether.domain.car.GetCarsUseCase
import com.example.gotogether.domain.user.User
import com.example.gotogether.domain.user.usecase.GetCurrentUserUseCase
import com.example.gotogether.domain.user.usecase.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarViewModel @Inject constructor(
    private val getCarsUseCase: GetCarsUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
): ViewModel() {
    private val _state = MutableStateFlow(MyCarState())
    val state = _state.asStateFlow()

    var updateResult by mutableStateOf<Result<ResponseDTO>?>(null)
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
                    cars = getCars(),
                    isLoading = false
                )
            }
        }
    }

    suspend fun getCars(): Result<List<Car>> = getCarsUseCase()
    suspend fun getCurrentUser(): Result<User>? = getCurrentUserUseCase()
    fun updateCar(userUuid: String, carId: Long?) {
        viewModelScope.launch {
            updateResult = updateUserUseCase(userUuid, UpdateUserRequest(carId = carId))
        }
    }


    data class MyCarState(
        val user: Result<User>? = null,
        val cars: Result<List<Car>>? = null,
        val isLoading: Boolean = false
    )
}