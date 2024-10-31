package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository

import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.remote.ApiService
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.LoginResponse
import javax.inject.Inject

interface UserRepository {
    suspend fun getUser(): LoginResponse?
}
