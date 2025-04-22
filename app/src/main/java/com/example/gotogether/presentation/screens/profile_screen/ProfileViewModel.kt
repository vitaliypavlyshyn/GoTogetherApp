package com.example.gotogether.presentation.screens.profile_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotogether.data.user.UpdateUserRequestDTO
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
class ProfileViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
): ViewModel() {
    private val _state = MutableStateFlow(UserState())
    val state = _state.asStateFlow()

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

    fun loadUser() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = getCurrentUser()
            _state.update {
                it.copy(user = result, isLoading = false)
            }
        }
    }

    fun updateProfilePhoto(userUuid: String, photo: ByteArray?, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                updateUserUseCase(userUuid, UpdateUserRequestDTO(pictureProfile = photo))
                onSuccess()
            } catch (e: Exception) {

            }
        }
    }

    suspend fun getCurrentUser(): Result<User>? {
        return getCurrentUserUseCase.invoke()

    }

    data class UserState(
        val user: Result<User>? = null,
        val isLoading: Boolean = false
    )
}