package com.example.gotogether.domain.registration

import com.example.gotogether.data.registration.RegistrationResponse
import com.example.gotogether.data.registration.repository.RegistrationRepository
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val repository: RegistrationRepository
) {
    suspend operator fun invoke(email: String,
                                password: String,
                                firstName: String,
                                lastName: String,
                                dateOfBirth: String): Result<ResponseDTO> {
        return repository.register(email, password, firstName, lastName, dateOfBirth)
    }
}