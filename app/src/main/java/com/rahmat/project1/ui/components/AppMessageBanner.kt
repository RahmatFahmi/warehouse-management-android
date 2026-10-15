package com.rahmat.project1.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rahmat.project1.ui.theme.AppDimens

enum class MessageType {
    SUCCESS, ERROR, INFO
}

@Composable
fun AppMessageBanner(
    modifier: Modifier = Modifier,
    message: String?,
    type: MessageType = MessageType.ERROR
) {
    val (backgroundColor, contentColor, icon) = when (type) {
        MessageType.SUCCESS -> Triple(
            Color(0xFFE8F5E9), // Light Green
            Color(0xFF2E7D32), // Dark Green
            Icons.Rounded.CheckCircle
        )
        MessageType.ERROR -> Triple(
            MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.9f),
            MaterialTheme.colorScheme.error,
            Icons.Rounded.Error
        )
        MessageType.INFO -> Triple(
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9f),
            MaterialTheme.colorScheme.primary,
            Icons.Rounded.Info
        )
    }

    AnimatedVisibility(
        visible = !message.isNullOrBlank(),
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut(),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(AppDimens.radiusSmall))
                .background(backgroundColor)
                .padding(AppDimens.spacing8),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(AppDimens.spacing8))
            Text(
                text = message ?: "",
                color = contentColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 18.sp
            )
        }
    }
}
