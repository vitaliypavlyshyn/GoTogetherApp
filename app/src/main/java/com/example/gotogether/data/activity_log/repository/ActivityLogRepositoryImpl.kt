package com.example.gotogether.data.activity_log.repository

import com.example.gotogether.data.activity_log.ActivityLogApiService
import com.example.gotogether.data.activity_log.ActivityLogRequest
import com.example.gotogether.data.activity_log.SaveActivityLogResponse
import com.example.gotogether.data.activity_log.toDomain
import com.example.gotogether.data.activity_log.toDomainList
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import com.example.gotogether.domain.activity_log.ActivityLog
import com.example.gotogether.domain.activity_log.SaveActivityLog
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Inject

class ActivityLogRepositoryImpl @Inject constructor(
    private val api: ActivityLogApiService,
) : ActivityLogRepository {
    override suspend fun getActivitiesByUserUuid(userUuid: String): Result<List<ActivityLog>> {
        return try {
            val activitiesLog = api.getActivitiesByUserUuid(userUuid)
            Result.success(activitiesLog.toDomainList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun postActivity(requestDTO: ActivityLogRequest): Result<ResponseDTO> {
        return try {
            val response = api.saveActivity(requestDTO)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}