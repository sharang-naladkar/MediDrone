package io.dronuts.medihelp.network

import io.dronuts.medihelp.auth.SecureTokenStore
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit

/**
 * OkHttp authenticator that attempts to refresh the token synchronously using a provided Retrofit instance.
 * On success, it updates the SecureTokenStore and returns a new request with Authorization header.
 */
class TokenAuthenticator(private val retrofitForAuth: Retrofit, private val tokenStore: SecureTokenStore) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        // Avoid infinite loop
        if (response.request.header("Authorization") == null) return null
        val refreshToken = tokenStore.refreshToken() ?: return null
        return try {
            val api = retrofitForAuth.create(ApiService::class.java)
            val refreshResp = runBlocking { api.refresh(RefreshRequest(refreshToken)) }
            tokenStore.saveTokens(refreshResp.access_token, refreshResp.refresh_token, refreshResp.expires_in)
            response.request.newBuilder().header("Authorization", "******").build()
        } catch (e: Exception) {
            // Refresh failed, give up
            null
        }
    }
}
