package com.rahmat.project1.common.utils.language

import android.app.Activity
import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel

/**
 * ViewModel reaktif untuk mengelola bahasa aktif di seluruh antarmuka Jetpack Compose.
 */
class LanguageViewModel(application: Application) : AndroidViewModel(application) {
    private val languageManager = LanguageManager(application)

    var currentLanguageCode by mutableStateOf(languageManager.getLanguage())
        private set

    fun selectLanguage(languageCode: String, activity: Activity? = null) {
        val normalizedCode = LanguageManager.normalizeLanguageCode(languageCode)
        currentLanguageCode = normalizedCode
        languageManager.setLanguage(normalizedCode, activity)
    }
}
