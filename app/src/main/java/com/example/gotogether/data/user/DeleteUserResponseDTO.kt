package com.example.gotogether.data.user

import com.example.gotogether.domain.user.DeleteUser

data class DeleteUserRequestDTO(
    val userUuid: String
)

data class DeleteUserResponseDTO(
    val success: Boolean,
    val message: String,
)

fun DeleteUserResponseDTO.toDomain(): DeleteUser {
    return DeleteUser(
        success = success,
        message = message
    )
}