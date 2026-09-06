package io.dronuts.medihelp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "incidents")
data class IncidentEntity(
    @PrimaryKey val id: String,
    val lat: Double,
    val lng: Double,
    val status: String,
    val timestamp: Long
)
