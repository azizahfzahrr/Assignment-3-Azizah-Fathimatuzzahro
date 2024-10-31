package com.example.app.data.repository

import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.DetailTravelResponse
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.remote.ApiService
import com.example.app.domain.repository.DestinationRepository
import retrofit2.Response
import javax.inject.Inject

class DestinationRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : DestinationRepository {
    override suspend fun getDestinationDetail(id: Int): Response<DetailTravelResponse> {
        return apiService.getDestinationDetail(id)
    }
}
