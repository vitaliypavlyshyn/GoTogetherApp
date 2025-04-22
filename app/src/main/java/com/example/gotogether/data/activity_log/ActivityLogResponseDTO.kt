package com.example.gotogether.data.activity_log

import com.example.gotogether.domain.activity_log.ActivityLog

data class ActivityLogResponseDTO(
    val userUuid: String,
    val device: String,
    val os: String,
    val publicIp: String,
    val entryDate: String,
)

fun ActivityLogResponseDTO.toDomain(): ActivityLog {
    return ActivityLog(
        userUuid = userUuid,
        device = device,
        os = os,
        publicIp = publicIp,
        entryDate = entryDate
    )
}

fun List<ActivityLogResponseDTO>.toDomainList(): List<ActivityLog> {
    val activitiesLog = mutableListOf<ActivityLog>()
    for(response in this) {
        activitiesLog.add(response.toDomain())
    }
    return activitiesLog
}