package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.LoginResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(@ApplicationContext private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

    private object PreferencesKeys {
        val EMAIL = stringPreferencesKey("email")
        val FIRST_NAME = stringPreferencesKey("first_name")
        val LAST_NAME = stringPreferencesKey("last_name")
        val PHONE = stringPreferencesKey("phone")
        val TOKEN = stringPreferencesKey("token")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val AVATAR = stringPreferencesKey("avatar")
    }

    data class UserProfile(
        val firstName: String?,
        val lastName: String?,
        val phone: String?,
        val email: String?,
        val avatar: String?
    )


    val isUserLoggedIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.IS_LOGGED_IN] ?: false
    }

    suspend fun setLoginStatus(isLoggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_LOGGED_IN] = isLoggedIn
        }
    }

    suspend fun saveUserData(userData: LoginResponse.Data) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.EMAIL] = userData.email ?: ""
            preferences[PreferencesKeys.FIRST_NAME] = userData.firstName ?: ""
            preferences[PreferencesKeys.LAST_NAME] = userData.lastName ?: ""
            preferences[PreferencesKeys.PHONE] = userData.phone ?: ""
            preferences[PreferencesKeys.TOKEN] = userData.token ?: ""
            preferences[PreferencesKeys.IS_LOGGED_IN] = true
            preferences[PreferencesKeys.AVATAR] = userData.avatar ?: ""
        }
    }

    suspend fun clearUserData() {
        context.dataStore.edit { preferences -> preferences.clear() }
    }

    suspend fun getUserToken(): String? {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.TOKEN]
        }.firstOrNull()
    }

    suspend fun getUserProfil(): UserProfile? {
        return context.dataStore.data.map { preferences ->
            UserProfile(
                firstName = preferences[PreferencesKeys.FIRST_NAME],
                lastName = preferences[PreferencesKeys.LAST_NAME],
                phone = preferences[PreferencesKeys.PHONE],
                email = preferences[PreferencesKeys.EMAIL],
                avatar = preferences[PreferencesKeys.AVATAR]
            )
        }.firstOrNull()
    }
}
