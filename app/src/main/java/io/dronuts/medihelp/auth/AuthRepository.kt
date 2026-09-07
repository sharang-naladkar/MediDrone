package io.dronuts.medihelp.auth

import io.dronuts.medihelp.network.ApiService
import io.dronuts.medihelp.network.RefreshRequest
import javax.inject.Inject

interface AuthRepository {
    suspend fun login(payload: Map<String, String>): Result<Unit>
    suspend fun refreshToken(): Result<Unit>
}

class AuthRepositoryImpl @Inject constructor(private val api: ApiService, private val store: SecureTokenStore) : AuthRepository {
    override suspend fun login(payload: Map<String, String>): Result<Unit> {
        return try {
            val resp = api.login(payload)
            store.saveTokens(resp.access_token, resp.refresh_token, resp.expires_in)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun refreshToken(): Result<Unit> {
        val refresh = store.refreshToken() ?: return Result.failure(Exception("No refresh token"))
        return try {
            val resp = api.refresh(RefreshRequest(refresh))
            store.saveTokens(resp.access_token, resp.refresh_token, resp.expires_in)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
