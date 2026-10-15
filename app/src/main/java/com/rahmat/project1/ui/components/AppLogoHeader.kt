package com.rahmat.project1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rahmat.project1.ui.theme.AppDimens

@Composable
fun AppLogoHeader(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(AppDimens.spacing64)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(AppDimens.radiusCard)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Inventory2,
                contentDescription = "App Logo",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(AppDimens.iconSizeLarge)
            )
        }

        Spacer(modifier = Modifier.height(AppDimens.spacing16))

        Text(
            text = title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(AppDimens.spacing4))

        Text(
            text = subtitle,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}