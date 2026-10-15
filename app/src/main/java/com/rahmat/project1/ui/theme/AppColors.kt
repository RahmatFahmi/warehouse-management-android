package com.rahmat.project1.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColor(
    // ─── Brand ────────────────────────────────────────────────────────────────
    val primary: Color,
    val primaryAlt: Color,
    val softTint: Color,
    val softTintAlt: Color,

    // ─── Surface & Background ─────────────────────────────────────────────────
    val background: Color,
    val surface: Color,
    val surfaceAlt: Color,
    val border: Color,
    val borderAlt: Color,

    // ─── Text ─────────────────────────────────────────────────────────────────
    val textPrimary: Color,
    val textSecondary: Color,
    val textOnPrimary: Color,

    // ─── Accent ───────────────────────────────────────────────────────────────
    val accentBlue: Color,
    val accentBlueAlt: Color,

    // ─── Semantic: Success ────────────────────────────────────────────────────
    val success: Color,
    val successContainer: Color,
    val onSuccess: Color,

    // ─── Semantic: Warning ────────────────────────────────────────────────────
    val warning: Color,
    val warningContainer: Color,
    val onWarning: Color,

    // ─── Semantic: Info ───────────────────────────────────────────────────────
    val info: Color,
    val infoContainer: Color,
    val onInfo: Color,

    // ─── Semantic: Danger ─────────────────────────────────────────────────────
    val danger: Color,
    val dangerContainer: Color,
    val onDanger: Color,
)

// ─── Light Token ──────────────────────────────────────────────────────────────
internal val LightAppColor = AppColor(
    primary             = OrangePrimary,
    primaryAlt          = OrangePrimaryAlt,
    softTint            = OrangeSoftTint,
    softTintAlt         = OrangeSoftTintAlt,

    background          = PageBackground,
    surface             = CardSurface,
    surfaceAlt          = BorderDividerAlt,
    border              = BorderDivider,
    borderAlt           = BorderDividerAlt,

    textPrimary         = DarkNavy,
    textSecondary       = SubtextGray,
    textOnPrimary       = CardSurface,

    accentBlue          = MutedBlue,
    accentBlueAlt       = MutedBlueAlt,

    success             = SuccessGreen,
    successContainer    = SuccessGreenBg,
    onSuccess           = CardSurface,

    warning             = WarningAmber,
    warningContainer    = WarningAmberBg,
    onWarning           = CardSurface,

    info                = InfoBlue,
    infoContainer       = InfoBlueBg,
    onInfo              = CardSurface,

    danger              = DangerRed,
    dangerContainer     = DangerRedBg,
    onDanger            = CardSurface,
)

// ─── Dark Token ───────────────────────────────────────────────────────────────
internal val DarkAppColor = AppColor(
    primary             = OrangePrimary,
    primaryAlt          = OrangePrimaryAlt,
    softTint            = Color(0xFF2D1A0E),   // tint gelap untuk dark mode
    softTintAlt         = Color(0xFF3D2010),

    background          = DarkBackground,
    surface             = DarkSurface,
    surfaceAlt          = DarkSurfaceAlt,
    border              = DarkBorder,
    borderAlt           = DarkBorderAlt,

    textPrimary         = CardSurface,
    textSecondary       = DarkSubtext,
    textOnPrimary       = CardSurface,

    accentBlue          = MutedBlue,
    accentBlueAlt       = MutedBlueAlt,

    success             = DarkSuccessGreen,
    successContainer    = DarkSuccessBg,
    onSuccess           = CardSurface,

    warning             = DarkWarningAmber,
    warningContainer    = DarkWarningBg,
    onWarning           = CardSurface,

    info                = DarkInfoBlue,
    infoContainer       = DarkInfoBg,
    onInfo              = CardSurface,

    danger              = DarkDangerRed,
    dangerContainer     = DarkDangerBg,
    onDanger            = CardSurface,
)

// ─── CompositionLocal ─────────────────────────────────────────────────────────
internal val LocalAppColor = staticCompositionLocalOf { LightAppColor }
