package io.dronuts.medihelp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.dronuts.medihelp.data.mqtt.MqttClient
import io.dronuts.medihelp.data.local.entities.IncidentEntity
import io.dronuts.medihelp.data.repository.IncidentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TelemetryPoint(val lat: Double, val lng: Double, val altitude: Double, val battery: Int)

@HiltViewModel
class IncidentViewModel @Inject constructor(
    private val repo: IncidentRepository,
    private val mqttClient: MqttClient
) : ViewModel() {
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

    fun simulateTelemetryFlow(incidentId: String): Flow<TelemetryPoint> = mqttClient.telemetry(incidentId)
}
