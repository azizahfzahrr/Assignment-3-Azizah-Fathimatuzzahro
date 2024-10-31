package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository

import android.util.Log
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.LoginResponse
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.local.UserPreferences
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.remote.ApiService
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiService: ApiService) : UserRepository {
    override suspend fun getUser(): LoginResponse? {
        val response = apiService.getUserProfile()
        return if (response.isSuccessful){
            response.body()
        } else null
    }
}


