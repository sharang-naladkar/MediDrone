package io.dronuts.medihelp.data.mqtt

import io.dronuts.medihelp.viewmodel.TelemetryPoint
import kotlinx.coroutines.flow.Flow

interface MqttClient {
    fun telemetry(incidentId: String): Flow<TelemetryPoint>
}
