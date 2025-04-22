package com.example.gotogether.data.registration

import com.example.gotogether.domain.registration.Registration

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