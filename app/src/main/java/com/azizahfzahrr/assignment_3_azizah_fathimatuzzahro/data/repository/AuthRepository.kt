package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository

import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.LoginResponse

interface AuthRepository {
    suspend fun login(email: String, password: String): LoginResponse
}