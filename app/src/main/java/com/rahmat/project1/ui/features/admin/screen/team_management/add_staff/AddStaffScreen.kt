package com.rahmat.project1.ui.features.admin.screen.team

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PrecisionManufacturing
import androidx.compose.material.icons.filled.Warehouse
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.rahmat.project1.R
import com.rahmat.project1.ui.common.UiState
import com.rahmat.project1.ui.components.AppButton
import com.rahmat.project1.ui.components.AppMessageBanner
import com.rahmat.project1.ui.components.AppTextField
import com.rahmat.project1.ui.features.admin.screen.team_management.add_staff.AddStaffViewModel
import com.rahmat.project1.ui.theme.AppDimens
import com.rahmat.project1.ui.theme.appColors

data class WarehouseOption(val id: String, val name: String)

@Composable
fun AddStaffScreen(
    onBackClick: () -> Unit = {},
    viewModel: AddStaffViewModel = hiltViewModel(),
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedWarehouseId by remember { mutableStateOf("") }
    var selectedWarehouseName by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("operational") }
    var isActive by remember { mutableStateOf(true) }
    var isWarehouseDropdownExpanded by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val createState by viewModel.createState.collectAsState()
    val getWarehousesState by viewModel.warehouses.collectAsState()

    LaunchedEffect(createState) {
        if (createState is UiState.Success) {
            showSuccessDialog = true
        }
    }

    if (showSuccessDialog) {
        com.rahmat.project1.ui.components.AppSuccessDialog(
            title = stringResource(R.string.add_staff_success_title),
            message = stringResource(R.string.add_staff_success_message),
            buttonText = stringResource(R.string.add_staff_success_button),
            onConfirm = {
                showSuccessDialog = false
                viewModel.resetCreateState()
                onBackClick()
            }
        )
    }

    val warehouseList = remember(getWarehousesState) {
        getWarehousesState.map { warehouses ->
            WarehouseOption(warehouses.id, warehouses.name)
        }
    }

    val isFormValid = fullName.isNotBlank() &&
            email.isNotBlank() &&
            password.isNotBlank() &&
            selectedWarehouseId.isNotBlank()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = AppDimens.screenPaddingHorizontal)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = AppDimens.spacing16),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.offset(x = (-12).dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.cd_back),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                Text(
                    text = stringResource(R.string.add_staff_title),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.offset(x = (-12).dp)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(AppDimens.spacing16)
            ) {
                Text(
                    text = stringResource(R.string.add_staff_subtitle),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                SectionLabel(text = stringResource(R.string.add_staff_section_account))

                AppTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = stringResource(R.string.add_staff_full_name_label),
                    placeholder = stringResource(R.string.add_staff_full_name_placeholder),
                    leadingIcon = Icons.Default.Person,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )

                AppTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = stringResource(R.string.add_staff_email_label),
                    placeholder = stringResource(R.string.add_staff_email_placeholder),
                    leadingIcon = Icons.Default.Email,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )

                AppTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = stringResource(R.string.add_staff_password_label),
                    placeholder = stringResource(R.string.password_placeholder),
                    leadingIcon = Icons.Default.Lock,
                    isPassword = true,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                )

                SectionLabel(text = stringResource(R.string.add_staff_section_warehouse))

                Box {
                    AppTextField(
                        value = selectedWarehouseName,
                        onValueChange = {},
                        label = stringResource(R.string.add_staff_warehouse_label),
                        placeholder = stringResource(R.string.add_staff_warehouse_placeholder),
                        leadingIcon = Icons.Default.Warehouse,
                        readOnly = true,
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable { isWarehouseDropdownExpanded = true }
                    )
                    DropdownMenu(
                        expanded = isWarehouseDropdownExpanded,
                        onDismissRequest = { isWarehouseDropdownExpanded = false },
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ) {
                        warehouseList.forEach { warehouse ->
                            DropdownMenuItem(
                                text = { Text(text = warehouse.name, fontSize = 14.sp) },
                                onClick = {
                                    selectedWarehouseId = warehouse.id
                                    selectedWarehouseName = warehouse.name
                                    isWarehouseDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                SectionLabel(text = stringResource(R.string.add_staff_section_role))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppDimens.spacing16)
                ) {
                    RoleSelectionCard(
                        modifier = Modifier.weight(1f),
                        title = stringResource(R.string.add_staff_role_operational_title),
                        description = stringResource(R.string.add_staff_role_operational_desc),
                        icon = Icons.Default.PrecisionManufacturing,
                        isSelected = role == "operational",
                        onClick = { role = "operational" }
                    )
                    RoleSelectionCard(
                        modifier = Modifier.weight(1f),
                        title = stringResource(R.string.add_staff_role_manager_title),
                        description = stringResource(R.string.add_staff_role_manager_desc),
                        icon = Icons.Default.AdminPanelSettings,
                        isSelected = role == "manager",
                        onClick = { role = "manager" }
                    )
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(AppDimens.radiusCard),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(AppDimens.spacing16),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = stringResource(R.string.add_staff_status_title),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(AppDimens.spacing4))
                            Text(
                                text = if (isActive) stringResource(R.string.add_staff_status_active) else stringResource(R.string.add_staff_status_inactive),
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = isActive,
                            onCheckedChange = { isActive = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                                checkedTrackColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(AppDimens.spacing24))
            }

            if (createState is UiState.Error) {
                Log.d("Error Tambah", "AddStaffScreen: ${(createState as UiState.Error).message}")
            }

            AppMessageBanner(
                message = if (createState is UiState.Error) (createState as UiState.Error).message else null
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = AppDimens.spacing16)
            ) {
                AppButton(
                    text = stringResource(R.string.add_staff_submit_button),
                    trailingIcon = Icons.Default.PersonAdd,
                    isLoading = createState is UiState.Loading,
                    enabled = isFormValid,
                    onClick = {
                        viewModel.addStaff(fullName, email, role, password, selectedWarehouseId, isActive)
                    }
                )
            }
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun RoleSelectionCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.appColors.border
    val containerColor = if (isSelected) MaterialTheme.appColors.softTint else MaterialTheme.colorScheme.surface

    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(AppDimens.radiusCard),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor)
    ) {
        Column(
            modifier = Modifier.padding(AppDimens.spacing16),
            verticalArrangement = Arrangement.spacedBy(AppDimens.spacing8)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(AppDimens.iconSizeMedium)
                )
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(R.string.cd_selected),
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(AppDimens.iconSizeSmall)
                    )
                }
            }
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = description,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}