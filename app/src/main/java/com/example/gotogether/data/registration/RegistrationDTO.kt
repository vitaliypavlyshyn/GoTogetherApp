package com.example.gotogether.data.registration

import com.example.gotogether.domain.registration.Registration

data class RegistrationDTO(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val email: String,
    val password: String
)

data class RegistrationResponseDTO(
    val success: Boolean,
    val message: String
)

fun RegistrationResponseDTO.toDomain(): Registration {
    return Registration(
        success = success,
        message = message
    )
}