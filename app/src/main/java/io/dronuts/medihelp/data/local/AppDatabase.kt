package io.dronuts.medihelp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import io.dronuts.medihelp.data.local.entities.IncidentEntity

@Database(entities = [IncidentEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun incidentDao(): IncidentDao
}
