package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository

import androidx.lifecycle.LiveData
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.room.ItineraryDao
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.room.ItineraryEntity
import javax.inject.Inject

class ItineraryRepositoryImpl @Inject constructor(
    private val itineraryDao: ItineraryDao
) : ItineraryRepository {

    override suspend fun insert(itinerary: ItineraryEntity) {
        itineraryDao.insert(itinerary)
    }

    override fun getAllItineraries(): LiveData<List<ItineraryEntity>> {
        return itineraryDao.getAllItineraries()
    }

    override suspend fun delete(itinerary: ItineraryEntity) {
        itineraryDao.delete(itinerary)
    }
}
