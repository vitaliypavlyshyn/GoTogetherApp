package com.example.gotogether.domain.user.usecase

import com.example.gotogether.data.trip_request.dto.ResponseDTO
import com.example.gotogether.data.user.repository.UserRepository
import com.example.gotogether.domain.user.DeleteUser
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userUuid: String): Result<ResponseDTO> {
        return repository.deleteUser(userUuid)
    }
}