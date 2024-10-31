package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "itinerary_table")
data class ItineraryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val location: String,
    val image: String,
    val duration: String,
    val popularity: String,
    val activity: String,
    val type: String,
    val notes: String
)


