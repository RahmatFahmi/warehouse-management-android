package com.rahmat.project1.ui.features.setting.components

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
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rahmat.project1.R
import com.rahmat.project1.ui.theme.AppDimens

enum class AppLanguageOption(
    val code: String,
    val titleRes: Int,
    val subtitleRes: Int,
    val flagEmoji: String
) {
    INDONESIAN(
        code = "in",
        titleRes = R.string.language_indonesian,
        subtitleRes = R.string.language_indonesian_sub,
        flagEmoji = "🇮🇩"
    ),
    ENGLISH(
        code = "en",
        titleRes = R.string.language_english,
        subtitleRes = R.string.language_english_sub,
        flagEmoji = "🇬🇧"
    );

    companion object {
        fun fromCode(code: String): AppLanguageOption {
            return entries.find {
                it.code.equals(code, ignoreCase = true) ||
                        (code.equals("id", ignoreCase = true) && it == INDONESIAN)
            } ?: INDONESIAN
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelectionBottomSheet(
    selectedLanguageCode: String,
    onLanguageSelected: (AppLanguageOption) -> Unit,
    onDismissRequest: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState()
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = AppDimens.radiusCard, topEnd = AppDimens.radiusCard)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppDimens.screenPaddingHorizontal)
                .padding(bottom = AppDimens.spacing32)
        ) {
            // Header
            Text(
                text = stringResource(R.string.select_language_title),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(AppDimens.spacing4))
            Text(
                text = stringResource(R.string.select_language_subtitle),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(AppDimens.spacing24))

            // Options List
            AppLanguageOption.entries.forEach { option ->
                val isSelected = option.code.equals(selectedLanguageCode, ignoreCase = true) ||
                        (selectedLanguageCode.equals("id", ignoreCase = true) && option == AppLanguageOption.INDONESIAN)

                LanguageOptionCard(
                    option = option,
                    isSelected = isSelected,
                    onClick = {
                        onLanguageSelected(option)
                        onDismissRequest()
                    }
                )
                Spacer(modifier = Modifier.height(AppDimens.spacing16))
            }
        }
    }
}

@Composable
private fun LanguageOptionCard(
    option: AppLanguageOption,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val animatedBorderColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
        animationSpec = tween(durationMillis = 150),
        label = "LanguageBorderColorAnimation"
    )

    val animatedContainerColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f) else MaterialTheme.colorScheme.surface,
        animationSpec = tween(durationMillis = 150),
        label = "LanguageContainerColorAnimation"
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
                // Flag Container
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = option.flagEmoji,
                        fontSize = 22.sp
                    )
                }

                Spacer(modifier = Modifier.width(AppDimens.spacing16))

                Column {
                    Text(
                        text = stringResource(option.titleRes),
                        fontSize = 15.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = stringResource(option.subtitleRes),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Selection Indicator
            Icon(
                imageVector = if (isSelected) Icons.Filled.CheckCircle else Icons.Outlined.Circle,
                contentDescription = null,
                tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
