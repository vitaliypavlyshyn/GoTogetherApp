package com.example.gotogether.presentation.screens.registration_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotogether.data.registration.RegistrationResponse
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import com.example.gotogether.domain.login.Login
import com.example.gotogether.domain.login.LoginUseCase
import com.example.gotogether.domain.registration.Registration
import com.example.gotogether.domain.registration.RegistrationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<Result<ResponseDTO>?>(null)
    val state: StateFlow<Result<ResponseDTO>?> = _state.asStateFlow()

    fun register(email: String, password: String, firstName: String, lastName: String, dateOfBirth: String) {
        viewModelScope.launch {
            _state.value = registrationUseCase(email, password, firstName, lastName, dateOfBirth)
        }
    }

    fun clearRegistrationState() {
        _state.value = null
    }
}