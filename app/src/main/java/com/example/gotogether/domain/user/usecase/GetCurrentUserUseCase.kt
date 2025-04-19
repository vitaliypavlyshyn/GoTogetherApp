package com.example.gotogether.domain.user.usecase

import com.example.gotogether.data.user.repository.UserRepository
import com.example.gotogether.domain.user.User

class GetCurrentUserUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(): Result<User> {
        return repository.getCurrentUser()
    }
}