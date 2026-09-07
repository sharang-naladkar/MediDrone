package io.dronuts.medihelp.mqtt

import kotlinx.coroutines.flow.Flow

interface MqttClient {
    suspend fun connect()
    suspend fun disconnect()
    fun telemetryFlow(topic: String): Flow<String>
    suspend fun subscribe(topic: String)
    suspend fun unsubscribe(topic: String)
}
