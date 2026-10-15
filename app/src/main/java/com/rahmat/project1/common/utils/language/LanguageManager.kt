package com.rahmat.project1.common.utils.language

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.LocaleList
import java.util.Locale

/**
 * Helper class untuk menyimpan dan menerapkan preferensi bahasa lokal (SharedPreferences + App Locale).
 */
class LanguageManager(private val context: Context) {
    private val prefs = context.getSharedPreferences("language_prefs", Context.MODE_PRIVATE)

    fun setLanguage(languageCode: String, activity: Activity? = null) {
        val normalizedCode = normalizeLanguageCode(languageCode)
        prefs.edit().putString("selected_language", normalizedCode).apply()

        applyLanguage(context, normalizedCode)

        activity?.recreate()
    }

    fun getLanguage(): String {
        val saved = prefs.getString("selected_language", "in") ?: "in"
        return normalizeLanguageCode(saved)
    }

    companion object {
        fun normalizeLanguageCode(code: String): String {
            return if (code.equals("id", ignoreCase = true)) "in" else code
        }

        fun applyLanguage(context: Context, languageCode: String) {
            val normalizedCode = normalizeLanguageCode(languageCode)
            val locale = Locale.forLanguageTag(normalizedCode)
            Locale.setDefault(locale)

            val resources = context.resources
            val config = Configuration(resources.configuration)
            config.setLocale(locale)
            config.setLocales(LocaleList(locale))

            @Suppress("DEPRECATION")
            resources.updateConfiguration(config, resources.displayMetrics)

            if (context.applicationContext != null && context.applicationContext != context) {
                @Suppress("DEPRECATION")
                context.applicationContext.resources.updateConfiguration(config, resources.displayMetrics)
            }
        }
    }
}
