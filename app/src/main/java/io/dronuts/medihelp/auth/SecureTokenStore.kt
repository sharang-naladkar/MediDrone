package io.dronuts.medihelp.auth

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import javax.inject.Inject

class SecureTokenStore @Inject constructor(context: Context) {
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val prefs = EncryptedSharedPreferences.create(
        "medihelp_tokens",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    companion object {
        private const val KEY_ACCESS = "access_token"
        private const val KEY_REFRESH = "refresh_token"
        private const val KEY_EXPIRY = "expiry"
    }

    fun saveTokens(access: String, refresh: String, expiresInSeconds: Long) {
        val expiry = System.currentTimeMillis() + (expiresInSeconds * 1000)
        prefs.edit().putString(KEY_ACCESS, access).putString(KEY_REFRESH, refresh).putLong(KEY_EXPIRY, expiry).apply()
    }

    fun clear() {
        prefs.edit().clear().apply()
    }

    fun accessToken(): String? = prefs.getString(KEY_ACCESS, null)
    fun refreshToken(): String? = prefs.getString(KEY_REFRESH, null)
    fun expiry(): Long = prefs.getLong(KEY_EXPIRY, 0L)
}
