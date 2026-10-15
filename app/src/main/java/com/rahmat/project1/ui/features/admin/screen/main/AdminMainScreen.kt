package com.rahmat.project1.ui.features.admin.screen.main

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rahmat.project1.R
import com.rahmat.project1.ui.features.admin.navigation.AdminBottomNavItem
import com.rahmat.project1.ui.features.admin.screen.dashboard.AdminDashboardScreen
import com.rahmat.project1.ui.features.admin.screen.master_data.main.MasterDataScreen
import com.rahmat.project1.ui.features.admin.screen.team_management.main.TeamManagementScreen
import com.rahmat.project1.ui.features.setting.screen.SettingScreen
import com.rahmat.project1.ui.theme.AppDimens

private val adminBottomNavItems = listOf(
    AdminBottomNavItem.Dashboard,
    AdminBottomNavItem.Team,
    AdminBottomNavItem.Master,
    AdminBottomNavItem.Setting
)

@Composable
fun AdminMainScreen(
    onAddWarehouseClick: () -> Unit = {},
    onAddStaffClick: () -> Unit = {},
    onAddSkuClick: () -> Unit = {},
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    var showExitDialog by rememberSaveable { mutableStateOf(false) }

    BackHandler(enabled = true) {
        showExitDialog = true
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text(stringResource(R.string.dialog_exit_title)) },
            text = { Text(stringResource(R.string.dialog_exit_message)) },
            confirmButton = {
                TextButton(onClick = {
                    (context as? Activity)?.finishAffinity()
                }) {
                    Text(stringResource(R.string.dialog_exit_confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text(stringResource(R.string.dialog_exit_cancel))
                }
            }
        )
    }

    Scaffold(
        bottomBar = {
            AdminBottomNavigation(
                navController = navController,
                items = adminBottomNavItems
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = AdminBottomNavItem.Dashboard.route
        ) {
            composable(AdminBottomNavItem.Dashboard.route) {
                AdminDashboardScreen()
            }
            composable(AdminBottomNavItem.Team.route) {
                TeamManagementScreen(
                    onAddStaffClick = onAddStaffClick
                )
            }
            composable(AdminBottomNavItem.Master.route) {
                MasterDataScreen(
                    onAddWarehouseClick = onAddWarehouseClick,
                    onAddSkuClick = onAddSkuClick
                )
            }
            composable(AdminBottomNavItem.Setting.route) {
                SettingScreen(
                    userName = "Budi Admin",
                    userEmail = "admin.budi@flowship.com",
                    userRole = "ADMIN UTAMA"
                )
            }
        }
    }
}

@Composable
fun AdminBottomNavigation(
    navController: NavHostController,
    items: List<AdminBottomNavItem>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = AppDimens.spacing8,
        modifier = Modifier.fillMaxWidth()
    ) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
        ) {
            items.forEach { item ->
                val isSelected = currentDestination?.hierarchy?.any { it.route == item.route } == true
                val labelText = stringResource(item.titleRes)

                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = labelText,
                            modifier = Modifier.size(AppDimens.iconSizeMedium)
                        )
                    },
                    label = { Text(labelText) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.surface,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                )
            }
        }
    }
}
