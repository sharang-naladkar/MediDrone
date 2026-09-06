package io.dronuts.medihelp.data.mqtt

import io.dronuts.medihelp.viewmodel.TelemetryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockMqttClient : MqttClient {
    override fun telemetry(incidentId: String): Flow<TelemetryPoint> = flow {
        val path = listOf(
            TelemetryPoint(12.9710, 77.5946, 10.0, 95),
            TelemetryPoint(12.9712, 77.5948, 30.0, 92),
            TelemetryPoint(12.9716, 77.5952, 60.0, 88),
            TelemetryPoint(12.9720, 77.5958, 80.0, 82),
            TelemetryPoint(12.9726, 77.5964, 40.0, 75),
            TelemetryPoint(12.9730, 77.5969, 20.0, 66),
            TelemetryPoint(12.9734, 77.5973, 5.0, 55)
        )
        for (point in path) {
            emit(point)
            delay(1200)
        }
    }
}
