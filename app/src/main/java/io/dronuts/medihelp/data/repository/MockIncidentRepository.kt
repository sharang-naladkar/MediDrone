package io.dronuts.medihelp.data.repository

import io.dronuts.medihelp.data.local.entities.IncidentEntity
import kotlinx.coroutines.delay

class MockIncidentRepository : IncidentRepository {
    private val stored = mutableListOf<IncidentEntity>()

    override suspend fun triggerEmergency(lat: Double, lng: Double, emergencyType: String): Result<String> {
        // Simulate network delay
        delay(800)
        val id = "demo-${System.currentTimeMillis()}"
        val now = System.currentTimeMillis()
        val ent = IncidentEntity(id = id, lat = lat, lng = lng, status = "dispatched", timestamp = now)
        stored.add(0, ent)
        return Result.success(id)
    }

    override suspend fun getIncidentStatus(incidentId: String): Result<IncidentEntity> {
        delay(300)
        val found = stored.find { it.id == incidentId } ?: return Result.failure(Exception("Not found"))
        return Result.success(found)
    }

    override suspend fun getHistory(): Result<List<IncidentEntity>> {
        delay(200)
        return Result.success(stored.toList())
    }
}
