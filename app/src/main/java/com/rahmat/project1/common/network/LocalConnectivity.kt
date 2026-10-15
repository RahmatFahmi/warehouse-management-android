package com.rahmat.project1.common.network

import androidx.compose.runtime.staticCompositionLocalOf

/**
 * CompositionLocal untuk menyediakan status koneksi jaringan ke seluruh UI.
 * Default bernilai true (menganggap tersambung sampai monitor memberikan data asli).
 */
val LocalNetworkStatus = staticCompositionLocalOf { true }
