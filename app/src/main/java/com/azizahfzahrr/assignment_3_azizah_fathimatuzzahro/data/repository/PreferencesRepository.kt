package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository

import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.PreferencesResponse

interface PreferencesRepository {
    suspend fun fetchTravelTypes(): PreferencesResponse?
}