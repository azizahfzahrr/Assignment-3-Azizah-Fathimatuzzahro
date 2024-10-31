package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [ItineraryEntity::class], version = 2)
abstract class ItineraryDatabase : RoomDatabase() {
    abstract fun itineraryDao(): ItineraryDao
}