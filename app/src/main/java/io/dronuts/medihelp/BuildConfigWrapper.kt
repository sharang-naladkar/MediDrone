package io.dronuts.medihelp

import android.content.SharedPreferences
import javax.inject.Inject

class BuildConfigWrapper @Inject constructor(private val prefs: SharedPreferences) {
    fun baseUrl(): String = BuildConfig.BASE_API_URL
}
