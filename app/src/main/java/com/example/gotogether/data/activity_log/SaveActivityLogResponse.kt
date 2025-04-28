package com.example.gotogether.data.activity_log

import com.example.gotogether.domain.activity_log.SaveActivityLog


data class SaveActivityLogResponse(
    val success: Boolean,
    val message: String
)

fun SaveActivityLogResponse.toDomain(): SaveActivityLog {
    return SaveActivityLog(
        success = success,
        message = message
    )
}
