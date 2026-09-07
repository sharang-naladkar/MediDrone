package io.dronuts.medihelp.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

data class TriggerRequest(val lat: Double, val lng: Double, val userId: String, val timestamp: Long, val emergencyType: String)
data class TriggerResponse(val incidentId: String, val status: String)

data class AuthResponse(val access_token: String, val refresh_token: String, val expires_in: Long)

data class RefreshRequest(val refresh_token: String)

data class HistoryItem(val id: String, val lat: Double, val lng: Double, val status: String, val timestamp: Long)

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body payload: Map<String, String>) : AuthResponse

    @POST("auth/refresh")
    suspend fun refresh(@Body payload: RefreshRequest): AuthResponse

    @POST("emergency/trigger")
    suspend fun trigger(@Body body: TriggerRequest): TriggerResponse

    @GET("emergency/{id}/status")
    suspend fun status(@Path("id") incidentId: String): TriggerResponse

    @GET("user/history")
    suspend fun history(): List<HistoryItem>
}
