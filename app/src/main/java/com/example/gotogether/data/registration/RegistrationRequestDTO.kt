package com.example.gotogether.data.registration

data class RegistrationRequestDTO(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val email: String,
    val password: String
)
