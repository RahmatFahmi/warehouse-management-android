package com.rahmat.project1.common.utils.theme

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel

/**
 * ViewModel reaktif untuk mengelola mode tema aplikasi (system, light, dark).
 */
class ThemeViewModel(application: Application) : AndroidViewModel(application) {
    private val themeManager = ThemeManager(application)

    var themeMode by mutableStateOf(themeManager.getThemeMode())
        private set

    fun updateThemeMode(mode: String) {
        themeMode = mode
        themeManager.setThemeMode(mode)
    }
}
