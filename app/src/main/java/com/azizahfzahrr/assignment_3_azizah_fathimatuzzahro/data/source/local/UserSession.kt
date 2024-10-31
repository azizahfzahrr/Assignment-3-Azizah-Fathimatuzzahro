package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.local

import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.LoginResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSession @Inject constructor(private val userPreferences: UserPreferences) {

    suspend fun saveUserData(data: LoginResponse.Data) {
        userPreferences.saveUserData(data)
        setLoginStatus(true)
    }

    suspend fun clearUserData() {
        userPreferences.clearUserData()
        setLoginStatus(false)
    }

    suspend fun setLoginStatus(isLoggedIn: Boolean) {
        userPreferences.setLoginStatus(isLoggedIn)
    }

    val isUserLoggedIn: Flow<Boolean> get() = userPreferences.isUserLoggedIn

    suspend fun getUserToken(): String? {
        return userPreferences.getUserToken()
    }
}