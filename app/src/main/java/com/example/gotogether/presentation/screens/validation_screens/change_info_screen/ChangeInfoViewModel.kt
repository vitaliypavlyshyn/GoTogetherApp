package com.example.gotogether.presentation.screens.validation_screens.change_info_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotogether.data.user.UpdateUserRequestDTO
import com.example.gotogether.domain.car.Car
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
class ChangeInfoViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(UserInfoState())
    val state = _state.asStateFlow()

    var updateResult by mutableStateOf<Result<String>?>(null)
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
    fun updateUser(userUuid: String, requestDTO: UpdateUserRequestDTO) {
        viewModelScope.launch {
            try {
                updateResult = updateUserUseCase(userUuid, requestDTO)
            } catch (e: Exception) {
                updateResult = Result.failure(e)
            }
        }
    }


    data class UserInfoState(
        val user: Result<User>? = null,
        val isLoading: Boolean = false
    )
}