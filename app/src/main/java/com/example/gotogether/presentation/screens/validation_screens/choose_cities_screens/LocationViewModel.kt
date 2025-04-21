package com.example.gotogether.presentation.screens.validation_screens.choose_cities_screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotogether.domain.location.GetLocationsUseCase
import com.example.gotogether.domain.location.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase
): ViewModel() {
    private val _state = MutableStateFlow(LocationsState())
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
                    locations = getLocations(),
                    isLoading = false
                )
            }
        }
    }

    suspend fun getLocations(): Result<List<Location>>? {
        return getLocationsUseCase.invoke()
    }

    data class LocationsState(
        val locations: Result<List<Location>>? = null,
        val isLoading: Boolean = false
    )
}