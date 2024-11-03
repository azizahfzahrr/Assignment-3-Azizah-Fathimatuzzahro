package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository

import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.ListResponse
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.TravelResponse

interface TravelRepository {
    suspend fun fetchTravelData(page: Int): TravelResponse?
    suspend fun fetchAllDestinations(page: Int): TravelResponse?
}