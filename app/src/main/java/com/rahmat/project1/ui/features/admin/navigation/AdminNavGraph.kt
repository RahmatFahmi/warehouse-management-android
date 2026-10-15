package com.rahmat.project1.ui.features.admin.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rahmat.project1.ui.features.admin.screen.main.AdminMainScreen
import com.rahmat.project1.ui.features.admin.screen.master_data.warehouse.AddWarehouseScreen
import com.rahmat.project1.ui.features.admin.screen.master_data.sku.AddSkuScreen
import com.rahmat.project1.ui.features.admin.screen.team.AddStaffScreen

fun NavGraphBuilder.adminNavGraph(
    navController: NavHostController,
){
    navigation(
        startDestination = AdminRoutes.AdminMain.route,
        route = "admin"
    ){
        composable(AdminRoutes.AdminMain.route) {
            AdminMainScreen(
                onAddWarehouseClick = {
                    navController.navigate(AdminRoutes.AdminAddWarehouse.route)
                },
                onAddStaffClick = {
                    navController.navigate(AdminRoutes.AdminAddStaff.route)
                },
                onAddSkuClick = {
                    navController.navigate(AdminRoutes.AdminAddSku.route)
                }
            )
        }
        composable(AdminRoutes.AdminAddWarehouse.route){
            AddWarehouseScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(AdminRoutes.AdminAddStaff.route){
            AddStaffScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(AdminRoutes.AdminAddSku.route){
            AddSkuScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
