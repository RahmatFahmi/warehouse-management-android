package com.rahmat.project1.ui.features.intro.navigation

sealed class IntroRoutes(val route: String) {
    data object Intro : IntroRoutes("intro/main")
}
