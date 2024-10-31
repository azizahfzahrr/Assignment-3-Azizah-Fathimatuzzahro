package com.example.app.domain.repository

import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.DetailTravelResponse
import retrofit2.Response

interface DestinationRepository {
    suspend fun getDestinationDetail(id: Int): Response<DetailTravelResponse>
}
