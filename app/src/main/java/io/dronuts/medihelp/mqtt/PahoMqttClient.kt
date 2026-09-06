package io.dronuts.medihelp.mqtt

import android.content.Context
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.util.*

class PahoMqttClient(private val context: Context, private val brokerUrl: String) : MqttClient(false) {
    // Note: using Eclipse Paho; implement a lightweight wrapper using callbackFlow.
    private val clientId = "medihelp-client-" + UUID.randomUUID().toString().take(8)
    private var client: MqttClient? = null

    init {
        // brokerUrl is expected to be like wss://broker.example:8883 or tcp://...
        client = MqttClient(brokerUrl, clientId, null)
    }

    override suspend fun connect() {
        val options = MqttConnectOptions()
        options.isCleanSession = true
        client?.connect(options)
    }

    override suspend fun disconnect() {
        client?.disconnect()
    }

    override fun telemetryFlow(topic: String): Flow<String> = callbackFlow {
        val cb = object : MqttCallback {
            override fun messageArrived(t: String?, message: MqttMessage?) {
                trySend(message?.toString() ?: "")
            }

            override fun connectionLost(cause: Throwable?) {}
            override fun deliveryComplete(token: IMqttDeliveryToken?) {}
        }
        client?.setCallback(cb)
        val listener = object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {}
            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {}
        }
        try {
            client?.subscribe(topic)
        } catch (e: Exception) {
            close(e)
        }
        awaitClose {
            try {
                client?.unsubscribe(topic)
            } catch (_: Exception) {}
        }
    }

    override suspend fun subscribe(topic: String) {
        client?.subscribe(topic)
    }

    override suspend fun unsubscribe(topic: String) {
        client?.unsubscribe(topic)
    }
}
