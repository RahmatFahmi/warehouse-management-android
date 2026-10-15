package com.rahmat.project1.ui.features.admin.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.rahmat.project1.R

sealed class AdminBottomNavItem(
    val route: String,
    @StringRes val titleRes: Int,
    val icon: ImageVector
) {
    object Dashboard : AdminBottomNavItem("admin_dashboard", R.string.nav_dashboard, Icons.Default.Home)
    object Team : AdminBottomNavItem("admin_team", R.string.nav_team, Icons.Default.Groups)
    object Master : AdminBottomNavItem("admin_master", R.string.nav_master_data, Icons.Default.Inventory2)
    object Setting : AdminBottomNavItem("admin_setting", R.string.nav_setting, Icons.Default.Settings)
}
