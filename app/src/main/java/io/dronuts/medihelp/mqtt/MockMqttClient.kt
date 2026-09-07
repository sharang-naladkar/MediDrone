package io.dronuts.medihelp.mqtt

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Simple mock mqtt client that emits JSON telemetry strings periodically for demo purposes.
 */
class MockMqttClient : MqttClient {
    private var connected = false

    override suspend fun connect() {
        connected = true
    }

    override suspend fun disconnect() {
        connected = false
    }

    override fun telemetryFlow(topic: String): Flow<String> = flow {
        val samples = listOf(
            "{\"lat\":12.9710,\"lng\":77.5946,\"alt\":10,\"battery\":95}",
            "{\"lat\":12.9712,\"lng\":77.5948,\"alt\":30,\"battery\":92}",
            "{\"lat\":12.9716,\"lng\":77.5952,\"alt\":60,\"battery\":88}",
            "{\"lat\":12.9720,\"lng\":77.5958,\"alt\":80,\"battery\":82}",
            "{\"lat\":12.9726,\"lng\":77.5964,\"alt\":40,\"battery\":75}",
            "{\"lat\":12.9730,\"lng\":77.5969,\"alt\":20,\"battery\":66}",
            "{\"lat\":12.9734,\"lng\":77.5973,\"alt\":5,\"battery\":55}"
        )
        for (s in samples) {
            emit(s)
            delay(1200)
        }
    }

    override suspend fun subscribe(topic: String) {}
    override suspend fun unsubscribe(topic: String) {}
}
