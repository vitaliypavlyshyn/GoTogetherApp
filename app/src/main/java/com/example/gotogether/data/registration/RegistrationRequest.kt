package com.example.gotogether.data.registration

import com.example.gotogether.domain.registration.Registration

data class RegistrationRequest(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val email: String,
    val password: String
)

data class RegistrationResponse(
    val isSuccess: Boolean,
    val message: String
)

fun RegistrationResponse.toDomain(): Registration {
    return Registration(
        isSuccess = isSuccess,
        message = message
    )
}