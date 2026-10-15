package com.rahmat.project1.ui.features.auth.navigation

sealed class AuthRoutes(val route: String) {
    data object Login : AuthRoutes("auth/login")
    data object Register : AuthRoutes("auth/register")
}