package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItineraryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(itinerary: ItineraryEntity)

    @Query("SELECT * FROM itinerary_table")
    fun getAllItineraries(): LiveData<List<ItineraryEntity>>

}

