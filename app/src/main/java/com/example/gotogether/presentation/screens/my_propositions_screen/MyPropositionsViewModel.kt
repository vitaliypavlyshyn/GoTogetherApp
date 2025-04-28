package com.example.gotogether.presentation.screens.my_propositions_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotogether.domain.trip.Trip
import com.example.gotogether.domain.trip.usecase.GetTripsByDriverUuid
import com.example.gotogether.domain.user.usecase.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPropositionsViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getTripsByDriverUuid: GetTripsByDriverUuid
): ViewModel(){
    private val _state = MutableStateFlow(MyPropositionsState())
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
                    trips = getMyPropositions(),
                    isLoading = false
                )
            }
        }
    }

   fun loadTrips() {
       viewModelScope.launch {
           _state.update { it.copy(isLoading = true) }
           val result = getMyPropositions()
           _state.update {
               it.copy(trips = result, isLoading = false)
           }
       }
   }
    suspend fun getMyPropositions(): Result<List<Trip>>? {
        val userResult = getCurrentUserUseCase()
        val user = userResult.getOrNull()
        if(user != null) {
            return getTripsByDriverUuid(user.userUuid)
        }
        return Result.failure(Exception("Користувача не знайдено"))
    }

    data class MyPropositionsState(
        val trips: Result<List<Trip>>? = null,
        val isLoading: Boolean = false
    )
}