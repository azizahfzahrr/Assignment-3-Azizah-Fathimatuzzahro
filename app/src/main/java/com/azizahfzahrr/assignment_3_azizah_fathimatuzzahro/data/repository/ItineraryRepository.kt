package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository

import androidx.lifecycle.LiveData
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.room.ItineraryDao
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.room.ItineraryEntity
import javax.inject.Inject

interface ItineraryRepository {
    suspend fun insert(itinerary: ItineraryEntity)
    fun getAllItineraries(): LiveData<List<ItineraryEntity>>
}