package com.example.gotogether.data.activity_log

import com.example.gotogether.domain.activity_log.SaveActivityLog


data class SaveActivityLogResponseDTO(
    val success: Boolean,
    val message: String
)

fun SaveActivityLogResponseDTO.toDomain(): SaveActivityLog {
    return SaveActivityLog(
        success = success,
        message = message
    )
}
