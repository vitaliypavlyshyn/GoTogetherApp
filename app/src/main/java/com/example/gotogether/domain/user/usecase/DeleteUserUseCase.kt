package com.example.gotogether.domain.user.usecase

import com.example.gotogether.data.user.repository.UserRepository
import com.example.gotogether.domain.user.DeleteUser
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userUuid: String): DeleteUser {
        return repository.deleteUser(userUuid)
    }
}