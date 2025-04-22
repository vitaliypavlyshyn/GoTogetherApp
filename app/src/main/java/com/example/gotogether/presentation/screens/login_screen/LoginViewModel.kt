package com.example.gotogether.presentation.screens.login_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotogether.data.activity_log.ActivityLogRequestDTO
import com.example.gotogether.domain.activity_log.PostActivityUseCase
import com.example.gotogether.domain.activity_log.SaveActivityLog
import com.example.gotogether.domain.login.Login
import com.example.gotogether.domain.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val postActivityUseCase: PostActivityUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<Login?>(null)
    val state: StateFlow<Login?> = _state.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.value = loginUseCase(email, password)
        }
    }

    fun clearLoginState() {
        _state.value = null
    }

    suspend fun saveActivity(request: ActivityLogRequestDTO): SaveActivityLog  {
        return postActivityUseCase(request)
    }
}