package com.rahmat.project1.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.rahmat.project1.ui.theme.AppDimens


@Composable
fun SkuCardSkeleton() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppDimens.radiusCard),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.spacing2)
    ) {
        Row(
            modifier = Modifier
                .padding(AppDimens.spacing16)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppDimens.spacing16),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(AppDimens.radiusSmall))
                    .shimmerEffect()
            )

            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(AppDimens.spacing8)) {
                Row(horizontalArrangement = Arrangement.spacedBy(AppDimens.spacing8)) {
                    Box(modifier = Modifier.width(60.dp).height(14.dp).clip(RoundedCornerShape(4.dp)).shimmerEffect())
                    Box(modifier = Modifier.width(40.dp).height(14.dp).clip(RoundedCornerShape(4.dp)).shimmerEffect())
                }
                Box(modifier = Modifier.fillMaxWidth(0.8f).height(16.dp).clip(RoundedCornerShape(4.dp)).shimmerEffect())
                Box(modifier = Modifier.fillMaxWidth(0.5f).height(12.dp).clip(RoundedCornerShape(4.dp)).shimmerEffect())
                Box(modifier = Modifier.width(100.dp).height(18.dp).clip(RoundedCornerShape(AppDimens.radiusFull)).shimmerEffect())
            }
        }
    }
}