package com.example.gotogether.domain.user.usecase

import com.example.gotogether.data.trip_request.dto.ResponseDTO
import com.example.gotogether.data.user.UpdateUserRequest
import com.example.gotogether.data.user.repository.UserRepository

class UpdateUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userUuid: String, request: UpdateUserRequest): Result<ResponseDTO> {
        return repository.updateUser(userUuid, request)
    }
}