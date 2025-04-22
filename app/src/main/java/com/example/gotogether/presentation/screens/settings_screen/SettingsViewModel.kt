package com.example.gotogether.presentation.screens.settings_screen

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotogether.domain.activity_log.ActivityLog
import com.example.gotogether.domain.activity_log.GetActivitiesUseCase
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
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val userUuid: String = savedStateHandle["userUuid"] ?: ""

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    init {
        loadActivities()
    }

    private fun loadActivities() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val result = getActivitiesUseCase(userUuid)

            _state.update {
                it.copy(
                    activityLogs = result,
                    isLoading = false,
                    errorMessage = if (result == null) "Не вдалося отримати логи" else null
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
        return getActivitiesUseCase.invoke(userUuid)
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
        val deleteResult: DeleteUser? = null,
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )
}