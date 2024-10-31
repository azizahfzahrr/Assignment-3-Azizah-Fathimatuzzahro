package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.domain.usecase

import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.TravelResponse
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository.TravelRepository

class FetchTravelDataUseCase (private val travelRepository: TravelRepository){
    suspend operator fun invoke(page: Int): TravelResponse? {
        return travelRepository.fetchTravelData(page)
    }
}