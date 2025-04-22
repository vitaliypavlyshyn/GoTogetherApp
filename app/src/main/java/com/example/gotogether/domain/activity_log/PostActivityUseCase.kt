package com.example.gotogether.domain.activity_log

import com.example.gotogether.data.activity_log.ActivityLogRequestDTO
import com.example.gotogether.data.activity_log.repository.ActivityLogRepository
import com.example.gotogether.data.user.repository.UserRepository
import javax.inject.Inject

class PostActivityUseCase @Inject constructor(
    private val repository: ActivityLogRepository
) {
    suspend operator fun invoke(request: ActivityLogRequestDTO): SaveActivityLog {
        return repository.postActivity(request)
    }
}