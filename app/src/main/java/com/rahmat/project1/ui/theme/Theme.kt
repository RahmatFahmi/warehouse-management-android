package com.rahmat.project1.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    // Brand
    primary                 = LightAppColor.primary,
    onPrimary               = LightAppColor.textOnPrimary,
    primaryContainer        = LightAppColor.softTint,
    onPrimaryContainer      = LightAppColor.primaryAlt,

    // Secondary (Accent Blue)
    secondary               = LightAppColor.accentBlue,
    onSecondary             = LightAppColor.textOnPrimary,
    secondaryContainer      = LightAppColor.softTintAlt,
    onSecondaryContainer    = LightAppColor.accentBlueAlt,

    // Tertiary (Navy)
    tertiary                = DarkNavy,
    onTertiary              = CardSurface,
    tertiaryContainer       = LightAppColor.softTintAlt,
    onTertiaryContainer     = DarkNavy,

    // Background & Surface
    background              = LightAppColor.background,
    onBackground            = LightAppColor.textPrimary,
    surface                 = LightAppColor.surface,
    onSurface               = LightAppColor.textPrimary,
    surfaceVariant          = LightAppColor.surfaceAlt,
    onSurfaceVariant        = LightAppColor.textSecondary,

    // Outline
    outline                 = LightAppColor.border,
    outlineVariant          = LightAppColor.borderAlt,

    // Error (mapped dari danger)
    error                   = LightAppColor.danger,
    onError                 = LightAppColor.onDanger,
    errorContainer          = LightAppColor.dangerContainer,
    onErrorContainer        = LightAppColor.danger,
)

private val DarkColorScheme = darkColorScheme(
    // Brand
    primary                 = DarkAppColor.primary,
    onPrimary               = DarkAppColor.textOnPrimary,
    primaryContainer        = DarkAppColor.softTint,
    onPrimaryContainer      = DarkAppColor.primaryAlt,

    // Secondary (Accent Blue)
    secondary               = DarkAppColor.accentBlue,
    onSecondary             = DarkAppColor.textOnPrimary,
    secondaryContainer      = DarkAppColor.softTintAlt,
    onSecondaryContainer    = DarkAppColor.accentBlueAlt,

    // Tertiary
    tertiary                = MutedBlue,
    onTertiary              = DarkNavy,
    tertiaryContainer       = DarkAppColor.softTintAlt,
    onTertiaryContainer     = CardSurface,

    // Background & Surface
    background              = DarkAppColor.background,
    onBackground            = DarkAppColor.textPrimary,
    surface                 = DarkAppColor.surface,
    onSurface               = DarkAppColor.textPrimary,
    surfaceVariant          = DarkAppColor.surfaceAlt,
    onSurfaceVariant        = DarkAppColor.textSecondary,

    // Outline
    outline                 = DarkAppColor.border,
    outlineVariant          = DarkAppColor.borderAlt,

    // Error (mapped dari danger)
    error                   = DarkAppColor.danger,
    onError                 = DarkAppColor.onDanger,
    errorContainer          = DarkAppColor.dangerContainer,
    onErrorContainer        = DarkAppColor.danger,
)

// ─── Theme Entry Point ────────────────────────────────────────────────────────

@Composable
fun Project1Theme(
    themeMode: String = "system",
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val systemInDark = isSystemInDarkTheme()
    val isDark = when (themeMode) {
        "dark" -> true
        "light" -> false
        else -> systemInDark
    }

    val appColor = if (isDark) DarkAppColor else LightAppColor

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (isDark) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        isDark -> DarkColorScheme
        else   -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDark
        }
    }

    CompositionLocalProvider(LocalAppColor provides appColor) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography  = Typography,
            content     = content
        )
    }
}

// ─── Accessor untuk warna custom ──────────────────────────────────────────────
//
// Dipakai di UI untuk token yang tidak ada slot-nya di Material:
//   success, successContainer, warning, info, danger, dll.
//
// Contoh pemakaian di Composable:
//   Text(color = MaterialTheme.appColors.success)
//   Box(modifier = Modifier.background(MaterialTheme.appColors.dangerContainer))

val MaterialTheme.appColors: AppColor
    @Composable
    @ReadOnlyComposable
    get() = LocalAppColor.current
