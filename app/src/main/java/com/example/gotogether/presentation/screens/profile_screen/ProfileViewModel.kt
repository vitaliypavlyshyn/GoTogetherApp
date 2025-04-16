package com.example.gotogether.presentation.screens.profile_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotogether.domain.user.User
import com.example.gotogether.domain.user.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _state = MutableStateFlow(UserState())
    val state = _state.asStateFlow()

    private val userUuid: String? = savedStateHandle.get<String>("userUuid")
    val fakeUuid = "dbb62adc-d975-4724-bf1a-c7c2de9dc908"
    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            _state.update {
                it.copy(
                    user = getUser(fakeUuid),
                    isLoading = false
                )
            }
        }
    }

    suspend fun getUser(userUuid: String?): User? {
        return userUuid?.let {
            getUserUseCase.invoke(it)
        }
    }

    data class UserState(
        val user: User? = null,
        val isLoading: Boolean = false
    )
}