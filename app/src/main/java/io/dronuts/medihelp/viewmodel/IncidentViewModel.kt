package io.dronuts.medihelp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.dronuts.medihelp.data.local.entities.IncidentEntity
import io.dronuts.medihelp.data.repository.IncidentRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TelemetryPoint(val lat: Double, val lng: Double, val altitude: Double, val battery: Int)

@HiltViewModel
class IncidentViewModel @Inject constructor(private val repo: IncidentRepository) : ViewModel() {
    private val _history = MutableStateFlow<List<IncidentEntity>>(emptyList())
    val history = _history.asStateFlow()

    private val _currentStatus = MutableStateFlow<IncidentEntity?>(null)
    val currentStatus = _currentStatus.asStateFlow()

    fun loadHistory() {
        viewModelScope.launch {
            val res = repo.getHistory()
            if (res.isSuccess) {
                _history.value = res.getOrNull() ?: emptyList()
            }
        }
    }

    fun triggerEmergency(lat: Double, lng: Double, emergencyType: String, onResult: (Result<String>) -> Unit) {
        viewModelScope.launch {
            val res = repo.triggerEmergency(lat, lng, emergencyType)
            if (res.isSuccess) {
                val id = res.getOrNull()!!
                // fetch and set current status from repo
                val st = repo.getIncidentStatus(id)
                if (st.isSuccess) {
                    _currentStatus.value = st.getOrNull()
                }
                onResult(Result.success(id))
            } else {
                onResult(Result.failure(res.exceptionOrNull() ?: Exception("Unknown")))
            }
        }
    }

    fun simulateTelemetryFlow(incidentId: String): Flow<TelemetryPoint> = flow {
        // simulate a path from a dispatch point to user's location
        val path = listOf(
            TelemetryPoint(12.9710, 77.5946, 10.0, 95),
            TelemetryPoint(12.9712, 77.5948, 30.0, 92),
            TelemetryPoint(12.9716, 77.5952, 60.0, 88),
            TelemetryPoint(12.9720, 77.5958, 80.0, 82),
            TelemetryPoint(12.9726, 77.5964, 40.0, 75),
            TelemetryPoint(12.9730, 77.5969, 20.0, 66),
            TelemetryPoint(12.9734, 77.5973, 5.0, 55)
        )
        for (p in path) {
            emit(p)
            delay(1200)
        }
    }
}
