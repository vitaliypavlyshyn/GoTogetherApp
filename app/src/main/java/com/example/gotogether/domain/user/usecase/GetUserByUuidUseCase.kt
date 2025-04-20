package com.example.gotogether.domain.user.usecase

import com.example.gotogether.data.user.repository.UserRepository
import com.example.gotogether.domain.user.User

class GetUserByUuidUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(userUuid: String): Result<User> {
        return repository.getUser(userUuid)
    }
}