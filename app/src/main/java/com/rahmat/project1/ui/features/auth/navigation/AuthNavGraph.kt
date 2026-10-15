package com.rahmat.project1.ui.features.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rahmat.project1.ui.features.auth.screen.login.LoginScreen
import com.rahmat.project1.ui.features.auth.screen.register.RegisterScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    onLoginSuccess: (role: String) -> Unit = {}
) {
    navigation(
        startDestination = AuthRoutes.Login.route,
        route = "auth"
    ){
        composable(AuthRoutes.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    onLoginSuccess(it)
                },
                onRegisterClick = {
                    navController.navigate(AuthRoutes.Register.route){
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(AuthRoutes.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(AuthRoutes.Login.route) {
                        popUpTo(AuthRoutes.Register.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onLoginClick = {
                    navController.navigate(AuthRoutes.Login.route){
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}