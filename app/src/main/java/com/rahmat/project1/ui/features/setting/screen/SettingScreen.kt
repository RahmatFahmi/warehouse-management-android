package com.rahmat.project1.ui.features.setting.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpCenter
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.activity.ComponentActivity
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rahmat.project1.R
import com.rahmat.project1.common.utils.language.LanguageViewModel
import com.rahmat.project1.common.utils.theme.ThemeViewModel
import com.rahmat.project1.ui.features.setting.components.AppLanguageOption
import com.rahmat.project1.ui.features.setting.components.AppThemeOption
import com.rahmat.project1.ui.features.setting.components.LanguageSelectionBottomSheet
import com.rahmat.project1.ui.features.setting.components.NotificationPreferencesUiState
import com.rahmat.project1.ui.features.setting.components.NotificationSettingsBottomSheet
import com.rahmat.project1.ui.features.setting.components.ThemeSelectionBottomSheet
import com.rahmat.project1.ui.theme.AppDimens
import com.rahmat.project1.ui.theme.appColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    userName: String = "Budi Santoso",
    userEmail: String = "budi@flowship.com",
    userRole: String = "ADMIN UTAMA",
    onLogoutClick: () -> Unit = {},
    onEditProfileClick: () -> Unit = {},
    onSecurityClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onLanguageClick: () -> Unit = {},
    onThemeClick: () -> Unit = {},
    onHelpCenterClick: () -> Unit = {},
    onAboutClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val activity = context as? ComponentActivity
    val languageViewModel: LanguageViewModel = if (activity != null) {
        viewModel(viewModelStoreOwner = activity)
    } else {
        viewModel()
    }
    val themeViewModel: ThemeViewModel = if (activity != null) {
        viewModel(viewModelStoreOwner = activity)
    } else {
        viewModel()
    }

    var showLanguageBottomSheet by rememberSaveable { mutableStateOf(false) }
    var showThemeBottomSheet by rememberSaveable { mutableStateOf(false) }

    var showNotificationBottomSheet by rememberSaveable { mutableStateOf(false) }
    var notificationPreferences by remember { mutableStateOf(NotificationPreferencesUiState()) }

    val currentLanguage = AppLanguageOption.fromCode(languageViewModel.currentLanguageCode)
    val currentLanguageSubtitle = "${currentLanguage.flagEmoji} ${stringResource(currentLanguage.titleRes)}"

    val currentTheme = AppThemeOption.fromCode(themeViewModel.themeMode)
    val currentThemeSubtitle = stringResource(currentTheme.titleRes)

    if (showLanguageBottomSheet) {
        LanguageSelectionBottomSheet(
            selectedLanguageCode = languageViewModel.currentLanguageCode,
            onLanguageSelected = { option ->
                languageViewModel.selectLanguage(option.code, activity)
                onLanguageClick()
            },
            onDismissRequest = {
                showLanguageBottomSheet = false
            }
        )
    }

    if (showThemeBottomSheet) {
        ThemeSelectionBottomSheet(
            selectedThemeCode = themeViewModel.themeMode,
            onThemeSelected = { option ->
                themeViewModel.updateThemeMode(option.code)
                onThemeClick()
            },
            onDismissRequest = {
                showThemeBottomSheet = false
            }
        )
    }

    if (showNotificationBottomSheet) {
        NotificationSettingsBottomSheet(
            initialState = notificationPreferences,
            onSavePreferences = { updated ->
                notificationPreferences = updated
                onNotificationsClick()
            },
            onDismissRequest = {
                showNotificationBottomSheet = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        Text(
            text = stringResource(R.string.setting_title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(
                horizontal = AppDimens.screenPaddingHorizontal,
                vertical = AppDimens.spacing16
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = AppDimens.screenPaddingHorizontal),
            verticalArrangement = Arrangement.spacedBy(AppDimens.spacing16)
        ) {
            // Profile Section
            ProfileCard(
                userName = userName,
                userEmail = userEmail,
                userRole = userRole,
                onEditClick = onEditProfileClick
            )

            // Account Section
            SettingSection(title = stringResource(R.string.setting_account_security_section)) {
                SettingItem(
                    icon = Icons.Default.Lock,
                    title = stringResource(R.string.setting_password_security),
                    subtitle = stringResource(R.string.setting_password_security_sub),
                    onClick = onSecurityClick
                )
                SettingItem(
                    icon = Icons.Default.Notifications,
                    title = stringResource(R.string.setting_notifications),
                    subtitle = stringResource(R.string.setting_notifications_sub),
                    onClick = {
                        showNotificationBottomSheet = true
                    }
                )
            }

            // System Section
            SettingSection(title = stringResource(R.string.setting_system_preferences_section)) {
                SettingItem(
                    icon = Icons.Default.Language,
                    title = stringResource(R.string.setting_language),
                    subtitle = currentLanguageSubtitle,
                    onClick = {
                        showLanguageBottomSheet = true
                    }
                )
                SettingItem(
                    icon = Icons.Default.DarkMode,
                    title = stringResource(R.string.setting_theme),
                    subtitle = currentThemeSubtitle,
                    onClick = {
                        showThemeBottomSheet = true
                    }
                )
            }

            // Information Section
            SettingSection(title = stringResource(R.string.setting_support_section)) {
                SettingItem(
                    icon = Icons.AutoMirrored.Filled.HelpCenter,
                    title = stringResource(R.string.setting_help_center),
                    subtitle = stringResource(R.string.setting_help_center_sub),
                    onClick = onHelpCenterClick
                )
                SettingItem(
                    icon = Icons.Default.Info,
                    title = stringResource(R.string.setting_about),
                    subtitle = stringResource(R.string.setting_about_sub),
                    onClick = onAboutClick
                )
            }

            // Logout Button
            LogoutButton(onClick = onLogoutClick)

            Spacer(modifier = Modifier.height(AppDimens.spacing32))
        }
    }
}

@Composable
private fun ProfileCard(
    userName: String,
    userEmail: String,
    userRole: String,
    onEditClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppDimens.radiusCard),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppDimens.cardPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppDimens.spacing16)
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (userName.isNotBlank()) userName.take(1).uppercase() else "U",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = userName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = userEmail,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(AppDimens.spacing4))
                Box(
                    modifier = Modifier
                        .background(
                            MaterialTheme.appColors.softTint,
                            RoundedCornerShape(AppDimens.radiusSmall)
                        )
                        .padding(horizontal = AppDimens.spacing8, vertical = AppDimens.spacing2)
                ) {
                    Text(
                        text = userRole,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            IconButton(onClick = onEditClick) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.setting_edit_profile),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun SettingSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(AppDimens.spacing8)) {
        Text(
            text = title,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = AppDimens.spacing4)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(AppDimens.radiusCard),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column {
                content()
            }
        }
    }
}

@Composable
private fun SettingItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(AppDimens.cardPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppDimens.spacing16)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    MaterialTheme.colorScheme.surfaceVariant,
                    RoundedCornerShape(AppDimens.radiusSmall)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp)
            )
        }

        Column(modifier = Modifier.weight(1f)) {
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

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.outline,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
private fun LogoutButton(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(AppDimens.radiusCard),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.appColors.dangerContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(AppDimens.cardPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppDimens.spacing8, Alignment.CenterHorizontally)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Logout,
                contentDescription = null,
                tint = MaterialTheme.appColors.danger,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = stringResource(R.string.setting_logout),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.appColors.danger
            )
        }
    }
}
