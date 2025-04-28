package com.example.gotogether.domain.activity_log

import com.example.gotogether.data.activity_log.ActivityLogRequest
import com.example.gotogether.data.activity_log.repository.ActivityLogRepository
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import javax.inject.Inject

class PostActivityUseCase @Inject constructor(
    private val repository: ActivityLogRepository
) {
    suspend operator fun invoke(request: ActivityLogRequest): Result<ResponseDTO> {
        return repository.postActivity(request)
    }
}