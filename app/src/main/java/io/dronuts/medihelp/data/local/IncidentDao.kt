package io.dronuts.medihelp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.dronuts.medihelp.data.local.entities.IncidentEntity

@Dao
interface IncidentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(incident: IncidentEntity)

    @Query("SELECT * FROM incidents ORDER BY timestamp DESC")
    suspend fun getAll(): List<IncidentEntity>

    @Query("SELECT * FROM incidents WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): IncidentEntity?
}
