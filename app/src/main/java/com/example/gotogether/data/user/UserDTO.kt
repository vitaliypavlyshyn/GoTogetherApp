package com.example.gotogether.data.user

import com.example.gotogether.domain.user.User

data class UserDTO(
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

fun UserDTO.toDomain(): User {
    return User(
        userUuid = userUuid,
        carId = carId,
        email = email,
        make = make,
        model = model,
        pictureProfile = pictureProfile,
        firstName = firstName,
        lastName = lastName,
        dateOfBirth = dateOfBirth,
        phoneNumber = phoneNumber,
        description = description,
        avgRating = avgRating,
        avgDrivingSkills = avgDrivingSkills,
        countReviews = countReviews,
        createdAt = createdAt,
        isDeleted = isDeleted
    )
}