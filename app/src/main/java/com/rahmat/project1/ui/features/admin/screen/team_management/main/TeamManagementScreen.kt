package com.rahmat.project1.ui.features.admin.screen.team_management.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PrecisionManufacturing
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warehouse
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rahmat.project1.R
import com.rahmat.project1.domain.model.WarehouseStaff
import com.rahmat.project1.ui.common.UiState
import com.rahmat.project1.ui.components.AppEmptyState
import com.rahmat.project1.ui.components.shimmerEffect
import com.rahmat.project1.ui.theme.AppDimens
import com.rahmat.project1.ui.theme.appColors

@Composable
fun TeamManagementScreen(
    onAddStaffClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onStaffClick: (WarehouseStaff) -> Unit = {},
    viewModel: TeamManagementViewModel = hiltViewModel()
) {
    var searchQuery by remember { mutableStateOf("") }

    val filterAll = stringResource(R.string.team_management_filter_all)
    val filterManager = stringResource(R.string.team_management_filter_manager)
    val filterOperator = stringResource(R.string.team_management_filter_operator)
    val filterOptions = remember(filterAll, filterManager, filterOperator) {
        listOf(filterAll, filterManager, filterOperator)
    }

    var selectedFilter by remember(filterAll) { mutableStateOf(filterAll) }

    val staffState by viewModel.staffState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = AppDimens.screenPaddingHorizontal)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(AppDimens.spacing8)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Inventory2,
                        contentDescription = stringResource(R.string.cd_logo),
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = stringResource(R.string.app_name),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                IconButton(
                    onClick = onNotificationClick
                ) {
                    BadgedBox(
                        badge = {
                            Badge(
                                containerColor = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(8.dp)
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = stringResource(R.string.cd_notifications),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(AppDimens.spacing16))

            Text(
                text = stringResource(R.string.team_management_title),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = stringResource(R.string.team_management_subtitle),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(AppDimens.spacing16))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = stringResource(R.string.team_management_search_placeholder),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.cd_search),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                shape = RoundedCornerShape(AppDimens.radiusCard),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.appColors.border
                )
            )

            Spacer(modifier = Modifier.height(AppDimens.spacing16))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(AppDimens.spacing8)
            ) {
                items(filterOptions, key = { it }) { filter ->
                    val isSelected = filter == selectedFilter
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(AppDimens.radiusFull))
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.surface
                            )
                            .then(
                                if (!isSelected) Modifier.border(
                                    width = 1.dp,
                                    color = MaterialTheme.appColors.border,
                                    shape = RoundedCornerShape(AppDimens.radiusFull)
                                ) else Modifier
                            )
                            .clickable { selectedFilter = filter }
                            .padding(
                                horizontal = AppDimens.spacing16,
                                vertical = AppDimens.spacing8
                            )
                    ) {
                        Text(
                            text = filter,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(AppDimens.spacing16))

            when (val state = staffState) {
                is UiState.Loading -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(AppDimens.spacing16),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(5) {
                            StaffCardSkeleton()
                        }
                    }
                }
                is UiState.Empty -> {
                    AppEmptyState(
                        title = stringResource(R.string.team_management_empty_title),
                        description = stringResource(R.string.team_management_empty_desc),
                        icon = Icons.Default.Group,
                        actionLabel = stringResource(R.string.team_management_add_staff_button),
                        onActionClick = onAddStaffClick,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                is UiState.Error -> {
                    AppEmptyState(
                        title = state.message,
                        description = stringResource(R.string.team_management_error_refresh_desc),
                        icon = Icons.Default.Error,
                        actionLabel = stringResource(R.string.team_management_retry_button),
                        onActionClick = { viewModel.refresh() },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                is UiState.Success -> {
                    val filteredList = remember(state.data, searchQuery, selectedFilter, filterManager, filterOperator) {
                        state.data.filter { staff ->
                            val fallbackName = "Staf"
                            val staffName = staff.userName ?: fallbackName
                            val matchesSearch = staffName.contains(searchQuery, ignoreCase = true)
                            val matchesFilter = when (selectedFilter) {
                                filterManager -> staff.role.equals("manager", ignoreCase = true)
                                filterOperator -> staff.role.equals("operational", ignoreCase = true) || staff.role.equals("operator", ignoreCase = true)
                                else -> true
                            }
                            matchesSearch && matchesFilter
                        }
                    }

                    if (filteredList.isEmpty()) {
                        AppEmptyState(
                            title = stringResource(R.string.team_management_search_empty_title),
                            description = stringResource(R.string.team_management_search_empty_desc),
                            icon = Icons.Default.Group,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(AppDimens.spacing16),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(filteredList, key = { it.id }) { staff ->
                                StaffCard(staff = staff, onClick = { onStaffClick(staff) })
                            }
                            item {
                                Spacer(modifier = Modifier.height(80.dp))
                            }
                        }
                    }
                }
                else -> {}
            }
        }

        Button(
            onClick = onAddStaffClick,
            shape = RoundedCornerShape(AppDimens.radiusCard),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = AppDimens.spacing24, end = AppDimens.screenPaddingHorizontal)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AppDimens.spacing8),
                modifier = Modifier.padding(vertical = AppDimens.spacing4)
            ) {
                Icon(
                    imageVector = Icons.Default.PersonAdd,
                    contentDescription = stringResource(R.string.cd_invite_staff)
                )
                Text(
                    text = stringResource(R.string.team_management_assign_staff_button),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun StaffCardSkeleton() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppDimens.radiusCard),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.spacing2)
    ) {
        Column(
            modifier = Modifier.padding(AppDimens.spacing16),
            verticalArrangement = Arrangement.spacedBy(AppDimens.spacing16)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(AppDimens.spacing16)
                ) {
                    Box(
                        modifier = Modifier
                            .size(AppDimens.spacing40)
                            .clip(CircleShape)
                            .shimmerEffect()
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(AppDimens.spacing4)) {
                        Box(
                            modifier = Modifier
                                .width(120.dp)
                                .height(16.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .shimmerEffect()
                        )
                        Box(
                            modifier = Modifier
                                .width(80.dp)
                                .height(12.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .shimmerEffect()
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .width(90.dp)
                    .height(20.dp)
                    .clip(RoundedCornerShape(AppDimens.radiusFull))
                    .shimmerEffect()
            )
        }
    }
}

@Composable
private fun StaffCard(
    staff: WarehouseStaff,
    onClick: () -> Unit
) {
    val fallbackStaffName = stringResource(R.string.team_management_staff_fallback_name)
    val name = staff.userName ?: fallbackStaffName
    val isManager = staff.role.equals("manager", ignoreCase = true)
    val badgeBg = if (isManager) MaterialTheme.appColors.infoContainer else MaterialTheme.appColors.successContainer
    val badgeColor = if (isManager) MaterialTheme.appColors.info else MaterialTheme.appColors.success
    val badgeIcon = if (isManager) Icons.Default.AdminPanelSettings else Icons.Default.PrecisionManufacturing
    val roleName = if (isManager) stringResource(R.string.team_management_role_manager) else stringResource(R.string.team_management_role_operational)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(AppDimens.radiusCard),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.spacing2)
    ) {
        Column(
            modifier = Modifier.padding(AppDimens.spacing16),
            verticalArrangement = Arrangement.spacedBy(AppDimens.spacing16)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(AppDimens.spacing16)
                ) {
                    Box(
                        modifier = Modifier
                            .size(AppDimens.spacing40)
                            .clip(CircleShape)
                            .background(MaterialTheme.appColors.softTint),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = name.take(1).uppercase(),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 16.sp
                        )
                    }

                    Column {
                        Text(
                            text = name,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (staff.isActive) stringResource(R.string.team_management_status_active) else stringResource(R.string.team_management_status_inactive),
                            fontSize = 11.sp,
                            color = if (staff.isActive) MaterialTheme.appColors.success else MaterialTheme.colorScheme.error
                        )
                    }
                }

                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = stringResource(R.string.cd_option),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Row(
                modifier = Modifier
                    .background(badgeBg, RoundedCornerShape(AppDimens.radiusFull))
                    .padding(horizontal = AppDimens.spacing8, vertical = AppDimens.spacing4),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AppDimens.spacing4)
            ) {
                Icon(
                    imageVector = badgeIcon,
                    contentDescription = null,
                    tint = badgeColor,
                    modifier = Modifier.size(AppDimens.iconSizeSmall)
                )
                Text(
                    text = roleName,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = badgeColor
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AppDimens.spacing8)
            ) {
                Icon(
                    imageVector = Icons.Default.Warehouse,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(AppDimens.iconSizeSmall)
                )
                Text(
                    text = stringResource(R.string.team_management_warehouse_assignment_label),
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = staff.warehouseName ?: stringResource(R.string.team_management_warehouse_fallback),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}