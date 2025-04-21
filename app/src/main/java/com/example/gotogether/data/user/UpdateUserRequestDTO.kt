package com.example.gotogether.data.user

data class UpdateUserRequestDTO (
    val carId: Long? = null,
    val pictureProfile: ByteArray? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val dateOfBirth: String? = null,
    val phoneNumber: String? = null,
    val description: String? = null
)