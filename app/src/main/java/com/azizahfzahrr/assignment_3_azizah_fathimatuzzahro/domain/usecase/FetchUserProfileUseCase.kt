package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.domain.usecase

import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.LoginResponse
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository.UserRepository

class FetchUserProfileUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(): LoginResponse? {
        return userRepository.getUser()
    }
}
