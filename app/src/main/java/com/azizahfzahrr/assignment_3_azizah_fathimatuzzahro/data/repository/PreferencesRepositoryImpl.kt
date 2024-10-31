package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository

import android.util.Log
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.PreferencesResponse
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.remote.ApiService
import java.io.IOException

class PreferencesRepositoryImpl(private val apiService: ApiService) : PreferencesRepository {
    override suspend fun fetchTravelTypes(): PreferencesResponse? {
        return try {
            val response = apiService.getTravelTypes()
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e(
                    "PreferencesRepository",
                    "API error: ${response.code()} - ${response.message()}"
                )
                null
            }
        } catch (e: IOException) {
            Log.e("PreferencesRepository", "Network error: ${e.message}", e)
            null
        } catch (e: Exception) {
            Log.e("PreferencesRepository", "Unexpected error: ${e.message}", e)
            null
        }
    }
}