package com.example.gotogether.domain.activity_log

import com.example.gotogether.data.activity_log.repository.ActivityLogRepository
import javax.inject.Inject

class GetActivitiesUseCase @Inject constructor(
    private val repository: ActivityLogRepository
) {
    suspend operator fun invoke(userUuid: String): Result<List<ActivityLog>> {
        return repository.getActivitiesByUserUuid(userUuid)
    }
}