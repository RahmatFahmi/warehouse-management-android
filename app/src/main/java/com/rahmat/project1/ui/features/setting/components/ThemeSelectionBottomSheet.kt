package com.rahmat.project1.ui.features.setting.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.AutoMode
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.rahmat.project1.ui.theme.AppDimens
import com.rahmat.project1.ui.theme.Project1Theme

/**
 * Enum representasi pilihan tema aplikasi.
 */
enum class AppThemeOption(
    val code: String,
    val titleRes: Int,
    val subtitleRes: Int,
    val icon: ImageVector,
    val lightAccentColor: Color,
    val darkAccentColor: Color,
) {
    SYSTEM(
        code = "system",
        titleRes = R.string.theme_system_title,
        subtitleRes = R.string.theme_system_sub,
        icon = Icons.Outlined.AutoMode,
        lightAccentColor = Color(0xFF00897B), // Teal
        darkAccentColor = Color(0xFF80CBC4)
    ),
    LIGHT(
        code = "light",
        titleRes = R.string.theme_light_title,
        subtitleRes = R.string.theme_light_sub,
        icon = Icons.Outlined.LightMode,
        lightAccentColor = Color(0xFFF57C00), // Amber / Warm Orange
        darkAccentColor = Color(0xFFFFB74D)
    ),
    DARK(
        code = "dark",
        titleRes = R.string.theme_dark_title,
        subtitleRes = R.string.theme_dark_sub,
        icon = Icons.Outlined.DarkMode,
        lightAccentColor = Color(0xFF3F51B5), // Deep Indigo / Dark Blue
        darkAccentColor = Color(0xFF9FA8DA)
    );

    companion object {
        fun fromCode(code: String): AppThemeOption {
            return entries.find { it.code.equals(code, ignoreCase = true) }
                ?: if (code.equals("dark", ignoreCase = true)) DARK else LIGHT
        }
    }
}

/**
 * Desain Pop-Up ModalBottomSheet untuk Pemilihan Tema Aplikasi.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSelectionBottomSheet(
    selectedThemeCode: String,
    onThemeSelected: (AppThemeOption) -> Unit,
    onDismissRequest: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState()
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = AppDimens.radiusCard, topEnd = AppDimens.radiusCard)
    ) {
        ThemeSelectionContent(
            selectedThemeCode = selectedThemeCode,
            onThemeSelected = { option ->
                onThemeSelected(option)
                onDismissRequest()
            }
        )
    }
}

/**
 * Alternatif Pop-Up Center Dialog untuk Pemilihan Tema Aplikasi.
 */
@Composable
fun ThemeSelectionDialog(
    selectedThemeCode: String,
    onThemeSelected: (AppThemeOption) -> Unit,
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
            ThemeSelectionContent(
                selectedThemeCode = selectedThemeCode,
                onThemeSelected = { option ->
                    onThemeSelected(option)
                    onDismissRequest()
                },
                modifier = Modifier.padding(vertical = AppDimens.spacing16)
            )
        }
    }
}

/**
 * Konten Utama Desain Pemilihan Tema (Reusable untuk BottomSheet & Dialog).
 */
@Composable
fun ThemeSelectionContent(
    selectedThemeCode: String,
    onThemeSelected: (AppThemeOption) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppDimens.screenPaddingHorizontal)
            .padding(bottom = AppDimens.spacing24)
    ) {
        // Top Drag Indicator / Decorative accent
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
        Text(
            text = stringResource(R.string.select_theme_title),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(AppDimens.spacing4))
        Text(
            text = stringResource(R.string.select_theme_subtitle),
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(AppDimens.spacing24))

        // Theme Options List
        AppThemeOption.entries.forEach { option ->
            val isSelected = option.code.equals(selectedThemeCode, ignoreCase = true)

            ThemeOptionCard(
                option = option,
                isSelected = isSelected,
                onClick = { onThemeSelected(option) }
            )
            Spacer(modifier = Modifier.height(AppDimens.spacing16))
        }
    }
}

/**
 * Kartu Opsi Tema Individu dengan Desain Modern, Icon Avatar Tinted, dan Selection Indicator.
 */
@Composable
private fun ThemeOptionCard(
    option: AppThemeOption,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val isDarkTheme = MaterialTheme.colorScheme.surface.red < 0.5f
    val accentColor = if (isDarkTheme) option.darkAccentColor else option.lightAccentColor

    val animatedBorderColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
        animationSpec = tween(durationMillis = 200),
        label = "BorderColorAnimation"
    )

    val animatedContainerColor by animateColorAsState(
        targetValue = if (isSelected) {
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.22f)
        } else {
            MaterialTheme.colorScheme.surface
        },
        animationSpec = tween(durationMillis = 200),
        label = "ContainerColorAnimation"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(AppDimens.radiusButton))
            .background(animatedContainerColor)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = animatedBorderColor,
                shape = RoundedCornerShape(AppDimens.radiusButton)
            )
            .clickable { onClick() }
            .padding(AppDimens.cardPadding)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Icon Avatar Box dengan Soft Accent Tint
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(accentColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = option.icon,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(AppDimens.spacing16))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(option.titleRes),
                            fontSize = 15.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        AnimatedVisibility(visible = isSelected) {
                            Row {
                                Spacer(modifier = Modifier.width(AppDimens.spacing8))
                                Surface(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = CircleShape
                                ) {
                                    Text(
                                        text = "Aktif",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = stringResource(option.subtitleRes),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.width(AppDimens.spacing8))

            // Selection Indicator Icon
            Icon(
                imageVector = if (isSelected) Icons.Filled.CheckCircle else Icons.Outlined.Circle,
                contentDescription = null,
                tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
