package com.rahmat.project1.ui.features.admin.navigation


sealed class AdminRoutes(val route: String) {
    data object AdminMain : AdminRoutes("admin/home")
    data object AdminAddWarehouse : AdminRoutes("admin/add_warehouse")
    data object AdminAddStaff : AdminRoutes("admin/add_staff")
    data object AdminAddSku : AdminRoutes("admin/add_sku")
}