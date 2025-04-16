package com.example.gotogether.domain.user.usecase

import com.example.gotogether.data.user.repository.UserRepository
import com.example.gotogether.domain.user.User

class GetUserUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(userUuid: String): User {
        return repository.getUser(userUuid)
    }
}