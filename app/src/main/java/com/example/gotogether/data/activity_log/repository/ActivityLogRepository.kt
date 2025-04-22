package com.example.gotogether.data.activity_log.repository

import com.example.gotogether.data.activity_log.ActivityLogRequestDTO
import com.example.gotogether.domain.activity_log.ActivityLog
import com.example.gotogether.domain.activity_log.SaveActivityLog

interface  ActivityLogRepository {

    suspend fun getActivitiesByUserUuid(userUuid: String): Result<List<ActivityLog>>

    suspend fun postActivity(request: ActivityLogRequestDTO): SaveActivityLog
}