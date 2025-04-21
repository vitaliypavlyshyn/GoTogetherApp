package com.example.gotogether.domain.user

import com.example.gotogether.data.user.UpdateUserRequestDTO

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
    val createdAt: String?
)

fun User.toUpdateUserRequestDTO(): UpdateUserRequestDTO {
    return UpdateUserRequestDTO(
        carId = this.carId,
        pictureProfile = this.pictureProfile,
        firstName = this.firstName,
        lastName = this.lastName,
        dateOfBirth = this.dateOfBirth,
        phoneNumber = this.phoneNumber,
        description = this.description
    )
}