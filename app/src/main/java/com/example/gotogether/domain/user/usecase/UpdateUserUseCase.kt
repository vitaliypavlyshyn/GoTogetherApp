package com.example.gotogether.domain.user.usecase

import com.example.gotogether.data.user.UpdateUserRequestDTO
import com.example.gotogether.data.user.repository.UserRepository

class UpdateUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userUuid: String, request: UpdateUserRequestDTO): Result<String> {
        return repository.updateUser(userUuid, request)
    }
}