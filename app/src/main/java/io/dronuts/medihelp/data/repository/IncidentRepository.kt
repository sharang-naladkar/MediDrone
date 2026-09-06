package io.dronuts.medihelp.data.repository

import io.dronuts.medihelp.data.local.entities.IncidentEntity

interface IncidentRepository {
    suspend fun triggerEmergency(lat: Double, lng: Double, emergencyType: String): Result<String>
    suspend fun getIncidentStatus(incidentId: String): Result<IncidentEntity>
    suspend fun getHistory(): Result<List<IncidentEntity>>
}
