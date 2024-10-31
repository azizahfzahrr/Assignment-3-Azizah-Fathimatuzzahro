package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.domain.usecase

import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.LoginResponse
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend fun executeLogin(email: String, password: String): LoginResponse {
        return authRepository.login(email, password)
    }
}