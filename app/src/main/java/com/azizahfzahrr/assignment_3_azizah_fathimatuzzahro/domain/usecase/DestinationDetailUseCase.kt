package com.example.app.domain.usecase

import android.util.Log
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.DetailTravelResponse
import com.example.app.domain.repository.DestinationRepository
import javax.inject.Inject

class DestinationDetailUseCase @Inject constructor(
    private val destinationRepository: DestinationRepository
) {
    suspend operator fun invoke(id: Int): DetailTravelResponse? {
        val response = destinationRepository.getDestinationDetail(id)
        return if (response.isSuccessful) {
            response.body()
        } else {
            Log.e("DestinationDetailUseCase", "Error fetching destination detail: ${response.errorBody()?.string()}")
            null
        }
    }
}
