package com.example.gotogether.domain.user

data class User(
    val userUuid: String,
    val carId: Long?,
    val email: String?,
    val make: String?,
    val model: String?,
    val pictureProfile: ByteArray?,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val phoneNumber: String?,
    val description: String?,
    val avgRating: Double?,
    val avgDrivingSkills: Double?,
    val countReviews: Int,
    val createdAt: String?,
    val isDeleted: Boolean
)
