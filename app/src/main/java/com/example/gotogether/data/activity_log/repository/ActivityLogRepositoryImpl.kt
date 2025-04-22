package com.example.gotogether.data.activity_log.repository

import com.example.gotogether.data.activity_log.ActivityLogApiService
import com.example.gotogether.data.activity_log.ActivityLogRequestDTO
import com.example.gotogether.data.activity_log.SaveActivityLogResponseDTO
import com.example.gotogether.data.activity_log.toDomain
import com.example.gotogether.data.activity_log.toDomainList
import com.example.gotogether.data.trip.toDomainList
import com.example.gotogether.domain.activity_log.ActivityLog
import com.example.gotogether.domain.activity_log.SaveActivityLog
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Inject

class  ActivityLogRepositoryImpl @Inject constructor(
    private val api: ActivityLogApiService,
    private val retrofit: Retrofit
): ActivityLogRepository {
    override suspend fun getActivitiesByUserUuid(userUuid: String): Result<List<ActivityLog>> {
        return try {
            val activitiesLog = api.getActivitiesByUserUuid(userUuid)
            Result.success(activitiesLog.toDomainList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun postActivity(requestDTO: ActivityLogRequestDTO): SaveActivityLog {
        val response = api.saveActivity(requestDTO)


        val errorConverter: Converter<ResponseBody, SaveActivityLogResponseDTO> =
            retrofit.responseBodyConverter(SaveActivityLogResponseDTO::class.java, arrayOf())

        val dto: SaveActivityLogResponseDTO = when {
            response.isSuccessful -> {
                response.body()!!
            }

            else -> {
                response.errorBody()?.let { errBody ->
                    try {
                        errorConverter.convert(errBody)
                    } catch (_: Exception) {
                        SaveActivityLogResponseDTO(
                            success = false,
                            message = "Server error ${response.code()}",
                        )
                    }
                } ?: SaveActivityLogResponseDTO(
                    success = false,
                    message = "Unknown error ${response.code()}",
                )
            }
        }


        return dto.toDomain()
    }
}