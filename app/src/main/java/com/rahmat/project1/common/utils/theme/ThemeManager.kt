package com.rahmat.project1.common.utils.theme

import android.content.Context

/**
 * Helper class untuk menyimpan dan mengambil preferensi tema aplikasi (system, light, dark).
 */
class ThemeManager(context: Context) {
    private val prefs = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)

    fun setThemeMode(mode: String) {
        prefs.edit().putString("theme_mode", mode).apply()
    }

    fun getThemeMode(): String {
        return prefs.getString("theme_mode", "system") ?: "system"
    }
}
