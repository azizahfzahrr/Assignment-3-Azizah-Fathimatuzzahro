package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.TravelResponse
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.remote.ApiService
import java.io.IOException

class TravelRepositoryImpl(private val apiService: ApiService) : TravelRepository {
    override suspend fun fetchTravelData(page: Int): TravelResponse? {
        return try {
            val response = apiService.getTravelData(page)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("TravelRepository", "API error: ${response.code()} - ${response.message()}")
                null
            }
        } catch (e: IOException) {
            Log.e("TravelRepository", "Network error: ${e.message}", e)
            null
        } catch (e: Exception) {
            Log.e("TravelRepository", "Unexpected error: ${e.message}", e)
            null
        }
    }
}