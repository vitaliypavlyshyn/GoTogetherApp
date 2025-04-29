package com.example.gotogether.presentation.screens.settings_screen

import android.content.Context
import android.os.Environment
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import com.example.gotogether.domain.activity_log.ActivityLog
import com.example.gotogether.domain.activity_log.GetActivitiesUseCase
import com.example.gotogether.domain.trip.Trip
import com.example.gotogether.domain.trip.toListTripExportDTO
import com.example.gotogether.domain.trip.usecase.GetTripsByDriverUuidUseCase
import com.example.gotogether.domain.trip.usecase.GetTripsByPassengerUuidUseCase
import com.example.gotogether.domain.user.DeleteUser
import com.example.gotogether.domain.user.usecase.DeleteUserUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val deleteUserUseCase: DeleteUserUseCase,
    private val getActivitiesUseCase: GetActivitiesUseCase,
    private val getTripsByPassengerUuidUseCase: GetTripsByPassengerUuidUseCase,
    private val getTripsByDriverUuidUseCase: GetTripsByDriverUuidUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val userUuid: String = savedStateHandle["userUuid"] ?: ""

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val resultActivity = getActivitiesUseCase(userUuid)
            val resultBookedTrips = getTripsByPassengerUuidUseCase(userUuid)
            val resultMyPropositions= getTripsByDriverUuidUseCase(userUuid)

            _state.update {
                it.copy(
                    activityLogs = resultActivity,
                    myBookedTrips = resultBookedTrips,
                    myPropositions = resultMyPropositions,
                    isLoading = false,

                    )
            }
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            val result = deleteUserUseCase(userUuid)
            _state.update { it.copy(deleteResult = result) }
        }
    }


    suspend fun getActivitiesLog(): Result<List<ActivityLog>>? {
        return getActivitiesUseCase(userUuid)
    }

    suspend fun getMyBookedTrips(): Result<List<Trip>>? {
        return getTripsByPassengerUuidUseCase(userUuid)
    }

    suspend fun getMyPropositions(): Result<List<Trip>>? {
        return getTripsByDriverUuidUseCase(userUuid)
    }

    fun exportToJson(
        context: Context,
        fileNamePrefix: String,
        dataSupplier: suspend () -> Result<List<Any>>?,
        onFailMessage: String
    ) {
        viewModelScope.launch {
            val result = dataSupplier()

            result?.onSuccess { list ->
                val json = Gson().toJson(list)
                val timestamp = Instant.now().toString().replace(":", "-")
                val fileName = "${fileNamePrefix}_$timestamp.json"

                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val file = File(downloadsDir, fileName)

                try {
                    file.writeText(json)
                    Toast.makeText(context, "Файл збережено: ${file.absolutePath}", Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(context, "Помилка збереження: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }?.onFailure {
                Toast.makeText(context, onFailMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun exportMyBookedTripsToJson(context: Context) {
        viewModelScope.launch {
            val result = getMyBookedTrips()

            result?.onSuccess { list ->
                val mappedList = list.toListTripExportDTO()
                val json = Gson().toJson(mappedList)

                val timestamp = Instant.now().toString().replace(":", "-")
                val fileName = "booked_trips_$timestamp.json"
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val file = File(downloadsDir, fileName)
                try {
                    file.writeText(json)
                    Toast.makeText(context, "Файл збережено: ${file.absolutePath}", Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(context, "Помилка збереження: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }?.onFailure {
                Toast.makeText(context, "Не вдалося отримати активність", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun exportMyPropositionsToJson(context: Context) {
        viewModelScope.launch {
            val result = getMyPropositions()

            result?.onSuccess { list ->
                val mappedList = list.toListTripExportDTO()
                val json = Gson().toJson(mappedList)

                val timestamp = Instant.now().toString().replace(":", "-")
                val fileName = "my_propositions_$timestamp.json"

                val file = File(context.getExternalFilesDir(null), fileName)
                try {
                    file.writeText(json)
                    Toast.makeText(context, "Файл збережено: ${file.absolutePath}", Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(context, "Помилка збереження: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }?.onFailure {
                Toast.makeText(context, "Не вдалося отримати активність", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun exportActivityLogToJson(context: Context) {
        viewModelScope.launch {
            val result = getActivitiesLog()

            result?.onSuccess { list ->
                val json = Gson().toJson(list)

                val timestamp = Instant.now().toString().replace(":", "-")
                val fileName = "activity_log_$timestamp.json"

                val file = File(context.getExternalFilesDir(null), fileName)
                try {
                    file.writeText(json)
                    Toast.makeText(context, "Файл збережено: ${file.absolutePath}", Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(context, "Помилка збереження: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }?.onFailure {
                Toast.makeText(context, "Не вдалося отримати активність", Toast.LENGTH_SHORT).show()
            }
        }
    }

    data class SettingsState(
        val activityLogs: Result<List<ActivityLog>>? = null,
        val myBookedTrips: Result<List<Trip>>? = null,
        val myPropositions: Result<List<Trip>>? = null,
        val deleteResult: Result<ResponseDTO>? = null,
        val isLoading: Boolean = false,
    )
}