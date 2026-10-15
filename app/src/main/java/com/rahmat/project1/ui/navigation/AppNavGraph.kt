package com.rahmat.project1.ui.navigation

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.rahmat.project1.ui.features.admin.navigation.adminNavGraph
import com.rahmat.project1.ui.features.auth.navigation.authNavGraph
import com.rahmat.project1.ui.features.intro.navigation.introNavGraph

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String = "intro",
    onOnboardingFinish: () -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { fadeIn(animationSpec = tween(200)) },
        exitTransition = { fadeOut(animationSpec = tween(150)) }
    ) {
        introNavGraph(
            navController = navController,
            onFinish = onOnboardingFinish
        )
        authNavGraph(
            navController = navController,
            onLoginSuccess = { role ->
                Log.d("Navigation", "Received role: '$role'")
                
                // Menentukan target route secara eksplisit ke screen utama masing-masing role
                val targetRoute = when (role.lowercase().trim()) {
                    "admin" -> "admin/home" // Navigasi langsung ke screen home admin
                    "manager" -> "admin/home" // Sementara arahkan ke admin home dulu
                    "operator" -> "admin/home" // Sementara arahkan ke admin home dulu
                    else -> "admin/home"
                }
                
                navController.navigate(targetRoute) {
                    popUpTo("auth") { 
                        inclusive = true 
                    }
                    launchSingleTop = true
                }
            }
        )
        adminNavGraph(
            navController = navController
        )
    }
}
