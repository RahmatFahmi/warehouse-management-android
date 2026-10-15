package com.rahmat.project1.ui.features.setting.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.rahmat.project1.R
import com.rahmat.project1.ui.components.AppButton
import com.rahmat.project1.ui.theme.AppDimens
import com.rahmat.project1.ui.theme.Project1Theme

/**
 * Data Model khusus untuk Preferensi Notifikasi (Hanya Desain UI State).
 */
data class NotificationPreferencesUiState(
    val isMasterEnabled: Boolean = true,
    val isOrdersEnabled: Boolean = true,
    val isInventoryEnabled: Boolean = true,
    val isTeamEnabled: Boolean = false,
    val isSystemEnabled: Boolean = true,
)

/**
 * Desain Pop-Up BottomSheet untuk Pengaturan Notifikasi.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationSettingsBottomSheet(
    initialState: NotificationPreferencesUiState = NotificationPreferencesUiState(),
    onSavePreferences: (NotificationPreferencesUiState) -> Unit = {},
    onDismissRequest: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState()
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = AppDimens.radiusCard, topEnd = AppDimens.radiusCard)
    ) {
        NotificationSettingsContent(
            initialState = initialState,
            onSave = { updated ->
                onSavePreferences(updated)
                onDismissRequest()
            }
        )
    }
}

/**
 * Alternatif Pop-Up Center Dialog untuk Pengaturan Notifikasi.
 */
@Composable
fun NotificationSettingsDialog(
    initialState: NotificationPreferencesUiState = NotificationPreferencesUiState(),
    onSavePreferences: (NotificationPreferencesUiState) -> Unit = {},
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppDimens.spacing8),
            shape = RoundedCornerShape(AppDimens.radiusCard),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            NotificationSettingsContent(
                initialState = initialState,
                onSave = { updated ->
                    onSavePreferences(updated)
                    onDismissRequest()
                },
                modifier = Modifier.padding(vertical = AppDimens.spacing16)
            )
        }
    }
}

/**
 * Konten Utama Pop-Up Pengaturan Notifikasi (Reusable).
 */
@Composable
fun NotificationSettingsContent(
    initialState: NotificationPreferencesUiState,
    onSave: (NotificationPreferencesUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    var preferences by remember { mutableStateOf(initialState) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppDimens.screenPaddingHorizontal)
            .padding(bottom = AppDimens.spacing24)
            .verticalScroll(rememberScrollState())
    ) {
        // Drag Indicator Line
        Box(
            modifier = Modifier
                .width(36.dp)
                .height(4.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.outlineVariant)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(AppDimens.spacing16))

        // Header Section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(AppDimens.spacing16))

            Column {
                Text(
                    text = stringResource(R.string.notification_settings_title),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = stringResource(R.string.notification_settings_subtitle),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(AppDimens.spacing24))

        // Master Switch Banner Card
        MasterNotificationCard(
            isEnabled = preferences.isMasterEnabled,
            onCheckedChange = { isChecked ->
                preferences = preferences.copy(isMasterEnabled = isChecked)
            }
        )

        Spacer(modifier = Modifier.height(AppDimens.spacing16))

        // Category Switches (Animasi saat master switch dinyalakan/dimatikan)
        AnimatedVisibility(
            visible = preferences.isMasterEnabled,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(AppDimens.radiusCard))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant,
                        shape = RoundedCornerShape(AppDimens.radiusCard)
                    )
                    .padding(AppDimens.cardPadding),
                verticalArrangement = Arrangement.spacedBy(AppDimens.spacing8)
            ) {
                NotificationToggleRow(
                    icon = Icons.Outlined.LocalShipping,
                    iconTint = Color(0xFF1E88E5), // Blue
                    title = stringResource(R.string.notification_orders_title),
                    subtitle = stringResource(R.string.notification_orders_sub),
                    isChecked = preferences.isOrdersEnabled,
                    onCheckedChange = { preferences = preferences.copy(isOrdersEnabled = it) }
                )

                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

                NotificationToggleRow(
                    icon = Icons.Outlined.Inventory2,
                    iconTint = Color(0xFFFB8C00), // Amber
                    title = stringResource(R.string.notification_inventory_title),
                    subtitle = stringResource(R.string.notification_inventory_sub),
                    isChecked = preferences.isInventoryEnabled,
                    onCheckedChange = { preferences = preferences.copy(isInventoryEnabled = it) }
                )

                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

                NotificationToggleRow(
                    icon = Icons.Outlined.Group,
                    iconTint = Color(0xFF43A047), // Green
                    title = stringResource(R.string.notification_team_title),
                    subtitle = stringResource(R.string.notification_team_sub),
                    isChecked = preferences.isTeamEnabled,
                    onCheckedChange = { preferences = preferences.copy(isTeamEnabled = it) }
                )

                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

                NotificationToggleRow(
                    icon = Icons.Outlined.Shield,
                    iconTint = Color(0xFF8E24AA), // Purple
                    title = stringResource(R.string.notification_system_title),
                    subtitle = stringResource(R.string.notification_system_sub),
                    isChecked = preferences.isSystemEnabled,
                    onCheckedChange = { preferences = preferences.copy(isSystemEnabled = it) }
                )
            }
        }

        Spacer(modifier = Modifier.height(AppDimens.spacing24))

        // Save Button
        AppButton(
            text = stringResource(R.string.notification_save_button),
            onClick = { onSave(preferences) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * Featured Master Switch Card di bagian atas pop-up.
 */
@Composable
private fun MasterNotificationCard(
    isEnabled: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppDimens.radiusCard),
        colors = CardDefaults.cardColors(
            containerColor = if (isEnabled) {
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)
            } else {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
            }
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = if (isEnabled) 1.5.dp else 1.dp,
            color = if (isEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppDimens.cardPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(
                            if (isEnabled) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.NotificationsActive,
                        contentDescription = null,
                        tint = if (isEnabled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(AppDimens.spacing16))

                Column {
                    Text(
                        text = stringResource(R.string.notification_master_title),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = stringResource(R.string.notification_master_sub),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.width(AppDimens.spacing8))

            Switch(
                checked = isEnabled,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                    checkedTrackColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

/**
 * Baris Item Toggle Kategori Notifikasi.
 */
@Composable
private fun NotificationToggleRow(
    icon: ImageVector,
    iconTint: Color,
    title: String,
    subtitle: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = AppDimens.spacing4),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(iconTint.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(AppDimens.spacing16))

            Column(modifier = Modifier.alpha(if (isChecked) 1f else 0.7f)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.width(AppDimens.spacing8))

        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                checkedTrackColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}

// ─── Previews ─────────────────────────────────────────────────────────────────

@Preview(name = "Notification Settings Light", showBackground = true)
@Composable
private fun NotificationSettingsBottomSheetPreview() {
    Project1Theme(themeMode = "light") {
        Surface {
            NotificationSettingsContent(
                initialState = NotificationPreferencesUiState(),
                onSave = {}
            )
        }
    }
}

@Preview(name = "Notification Settings Dark", showBackground = true)
@Composable
private fun NotificationSettingsDarkPreview() {
    Project1Theme(themeMode = "dark") {
        Surface {
            NotificationSettingsContent(
                initialState = NotificationPreferencesUiState(),
                onSave = {}
            )
        }
    }
}

@Preview(name = "Notification Settings Dialog", showBackground = true)
@Composable
private fun NotificationSettingsDialogPreview() {
    Project1Theme(themeMode = "light") {
        NotificationSettingsDialog(
            initialState = NotificationPreferencesUiState(),
            onSavePreferences = {},
            onDismissRequest = {}
        )
    }
}
