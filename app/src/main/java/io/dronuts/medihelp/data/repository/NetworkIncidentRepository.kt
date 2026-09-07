package io.dronuts.medihelp.data.repository

import android.content.Context
import io.dronuts.medihelp.data.local.AppDatabase
import io.dronuts.medihelp.data.local.entities.IncidentEntity
import io.dronuts.medihelp.network.ApiService
import io.dronuts.medihelp.network.HistoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkIncidentRepository @Inject constructor(private val api: ApiService, private val context: Context) : IncidentRepository {
    private val db = AppDatabase::class.java // placeholder - use DI to get DB in future

    override suspend fun triggerEmergency(lat: Double, lng: Double, emergencyType: String): Result<String> {
        return try {
            val req = io.dronuts.medihelp.network.TriggerRequest(lat, lng, "demo-user", System.currentTimeMillis(), emergencyType)
            val resp = api.trigger(req)
            Result.success(resp.incidentId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getIncidentStatus(incidentId: String): Result<IncidentEntity> {
        return try {
            val resp = api.status(incidentId)
            val ent = IncidentEntity(id = resp.incidentId, lat = 0.0, lng = 0.0, status = resp.status, timestamp = System.currentTimeMillis())
            Result.success(ent)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getHistory(): Result<List<IncidentEntity>> {
        return try {
            val list: List<HistoryItem> = api.history()
            val mapped = list.map { IncidentEntity(id = it.id, lat = it.lat, lng = it.lng, status = it.status, timestamp = it.timestamp) }
            Result.success(mapped)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
