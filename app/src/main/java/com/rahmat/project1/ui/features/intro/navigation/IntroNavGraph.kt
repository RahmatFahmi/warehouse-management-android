package com.rahmat.project1.ui.features.intro.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rahmat.project1.ui.features.intro.screen.IntroScreen

fun NavGraphBuilder.introNavGraph(
    navController: NavHostController,
    onFinish: () -> Unit
) {
    navigation(
        startDestination = IntroRoutes.Intro.route,
        route = "intro"
    ) {
        composable(IntroRoutes.Intro.route) {
            IntroScreen(onFinish = onFinish)
        }
    }
}
