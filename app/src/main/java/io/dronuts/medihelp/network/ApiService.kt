package io.dronuts.medihelp.network

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Body

data class TriggerRequest(val lat: Double, val lng: Double, val userId: String, val timestamp: Long, val emergencyType: String)
data class TriggerResponse(val incidentId: String, val status: String)

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body payload: Map<String, String>) : Map<String, String>

    @POST("emergency/trigger")
    suspend fun trigger(@Body body: TriggerRequest): TriggerResponse

    @GET("emergency/{id}/status")
    suspend fun status(@Path("id") incidentId: String): TriggerResponse
}
