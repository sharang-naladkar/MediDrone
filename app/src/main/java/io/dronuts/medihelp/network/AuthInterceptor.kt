package io.dronuts.medihelp.network

import io.dronuts.medihelp.auth.SecureTokenStore
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val store: SecureTokenStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()
        val token = store.accessToken()
        return if (token != null) {
            val newReq = req.newBuilder().header("Authorization", "Bearer $token").build()
            chain.proceed(newReq)
        } else {
            chain.proceed(req)
        }
    }
}
