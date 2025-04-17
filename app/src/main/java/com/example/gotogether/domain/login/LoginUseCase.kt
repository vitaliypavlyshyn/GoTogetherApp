package com.example.gotogether.domain.login

import com.example.gotogether.data.login.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(email: String, password: String): Login {
        return repository.login(email, password)
    }
}