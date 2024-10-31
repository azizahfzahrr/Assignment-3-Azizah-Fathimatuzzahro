package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository

import androidx.lifecycle.LiveData
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.room.ItineraryDao
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.room.ItineraryEntity
import javax.inject.Inject

interface ItineraryRepository {
    suspend fun insert(itinerary: ItineraryEntity)
    fun getAllItineraries(): LiveData<List<ItineraryEntity>>
}

//class ItineraryRepository @Inject constructor(private val itineraryDao: ItineraryDao) {
//
//    fun getAllItineraries(): LiveData<List<ItineraryEntity>> {
//        return itineraryDao.getAllItineraries()
//    }
//
//    suspend fun insert(itinerary: ItineraryEntity) {
//        itineraryDao.insert(itinerary)
//    }
//}



//class ItineraryRepository @Inject constructor(private val itineraryDao: ItineraryDao) {
//
//    suspend fun insert(itinerary: ItineraryEntity) {
////        val entity = ItineraryEntity(
////            id = itinerary.id,
////            name = itinerary.name,
////            location = itinerary.location,
////            duration = itinerary.duration,
////            activity = itinerary.activity,
////            image = itinerary.image,
////            popularity = itinerary.popularity
////        )
//        itineraryDao.insertItinerary(itinerary)
//    }
//
//     fun getAllItineraries(): LiveData<List<ItineraryEntity>> {
//        return itineraryDao.getAllItineraries()
//    }
//}


