package com.rahmat.project1.ui.features.admin.screen.dashboard

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Warehouse
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rahmat.project1.ui.theme.AppDimens
import com.rahmat.project1.ui.theme.appColors

@Immutable
private data class BarData(val heightFraction: Float, val isOutlined: Boolean, val isWarning: Boolean = false)

private val barChartData = listOf(
    BarData(0.4f, false),
    BarData(0.6f, true),
    BarData(0.35f, false),
    BarData(0.8f, false),
    BarData(0.5f, true),
    BarData(0.9f, false),
    BarData(0.3f, false, isWarning = true)
)

@Composable
fun AdminDashboardScreen(
    onNotificationClick: () -> Unit = {},
    onDetailChartClick: () -> Unit = {},
    onSeeAllActivitiesClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(bottom = AppDimens.spacing24)
    ) {
        DashboardHeader(onNotificationClick = onNotificationClick)

        Spacer(modifier = Modifier.height(AppDimens.spacing24))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppDimens.screenPaddingHorizontal),
            verticalArrangement = Arrangement.spacedBy(AppDimens.spacing16)
        ) {
            SummaryGridSection()
            ChartSection(onDetailClick = onDetailChartClick)
            RecentActivitySection(onSeeAllClick = onSeeAllActivitiesClick)
        }
    }
}

@Composable
private fun DashboardHeader(
    onNotificationClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = AppDimens.screenPaddingHorizontal,
                vertical = AppDimens.spacing8
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppDimens.spacing8)
        ) {
            Box(
                modifier = Modifier
                    .size(AppDimens.spacing40)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "B",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Column {
                Text(
                    text = "Halo, Budi",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Box(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.appColors.softTint,
                            shape = RoundedCornerShape(AppDimens.radiusSmall)
                        )
                        .padding(horizontal = AppDimens.spacing8, vertical = AppDimens.spacing2)
                ) {
                    Text(
                        text = "PT LOGISTIK",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        IconButton(onClick = onNotificationClick) {
            BadgedBox(
                badge = {
                    Badge(
                        modifier = Modifier.size(AppDimens.spacing8),
                        containerColor = MaterialTheme.appColors.danger
                    )
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifikasi",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun SummaryGridSection() {
    Column(verticalArrangement = Arrangement.spacedBy(AppDimens.spacing8)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppDimens.spacing8)
        ) {
            SummaryCard(
                modifier = Modifier.weight(1f),
                title = "TOTAL STOK",
                value = "12,450",
                subtitle = "4.2% dari bulan lalu",
                subtitleColor = MaterialTheme.appColors.success,
                subtitleIcon = Icons.Default.ArrowUpward,
                icon = Icons.Default.Inventory2,
                iconBgColor = MaterialTheme.appColors.infoContainer,
                iconColor = MaterialTheme.appColors.info
            )
            SummaryCard(
                modifier = Modifier.weight(1f),
                title = "TOTAL HUB",
                value = "8",
                subtitle = "Tersebar di 3 Pulau",
                subtitleColor = MaterialTheme.colorScheme.onSurfaceVariant,
                icon = Icons.Default.Warehouse,
                iconBgColor = MaterialTheme.appColors.softTint,
                iconColor = MaterialTheme.colorScheme.primary
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppDimens.spacing8)
        ) {
            SummaryCard(
                modifier = Modifier.weight(1f),
                title = "TOTAL STAF",
                value = "142",
                subtitle = "130 Aktif Hari Ini",
                subtitleColor = MaterialTheme.appColors.success,
                subtitleIcon = Icons.Default.CheckCircle,
                icon = Icons.Default.Group,
                iconBgColor = MaterialTheme.colorScheme.surfaceVariant,
                iconColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
            SummaryCard(
                modifier = Modifier.weight(1f),
                title = "SHIPMENT AKTIF",
                value = "84",
                subtitle = "12 Butuh Perhatian",
                subtitleColor = MaterialTheme.colorScheme.primary,
                icon = Icons.Default.LocalShipping,
                iconBgColor = MaterialTheme.colorScheme.primary,
                iconColor = MaterialTheme.colorScheme.onPrimary,
                valueColor = MaterialTheme.colorScheme.primary,
                titleColor = MaterialTheme.colorScheme.primary,
                isHighlight = true
            )
        }
    }
}

@Composable
private fun SummaryCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    subtitle: String,
    subtitleColor: Color,
    icon: ImageVector,
    iconBgColor: Color,
    iconColor: Color,
    subtitleIcon: ImageVector? = null,
    valueColor: Color = MaterialTheme.colorScheme.onSurface,
    titleColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    isHighlight: Boolean = false
) {
    Card(
        modifier = modifier.height(120.dp),
        shape = RoundedCornerShape(AppDimens.radiusCard),
        colors = CardDefaults.cardColors(
            containerColor = if (isHighlight) MaterialTheme.appColors.softTint else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.spacing2)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppDimens.spacing8),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = title,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = titleColor
                )
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(iconBgColor, RoundedCornerShape(AppDimens.radiusSmall)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(AppDimens.iconSizeSmall)
                    )
                }
            }

            Column {
                Text(
                    text = value,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = valueColor
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(AppDimens.spacing2)
                ) {
                    subtitleIcon?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = null,
                            tint = subtitleColor,
                            modifier = Modifier.size(10.dp)
                        )
                    }
                    Text(
                        text = subtitle,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = subtitleColor
                    )
                }
            }
        }
    }
}

@Composable
private fun ChartSection(onDetailClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppDimens.radiusCard),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.spacing2)
    ) {
        Column(modifier = Modifier.padding(AppDimens.spacing16)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Pergerakan Stok (7 Hari)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(
                    modifier = Modifier.clickable { onDetailClick() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Detail",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(AppDimens.iconSizeSmall)
                    )
                }
            }

            Spacer(modifier = Modifier.height(AppDimens.spacing16))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(AppDimens.radiusSmall)
                    )
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.appColors.border,
                        shape = RoundedCornerShape(AppDimens.radiusSmall)
                    )
                    .padding(horizontal = AppDimens.spacing8, vertical = AppDimens.spacing8),
                contentAlignment = Alignment.BottomCenter
            ) {
                val warningColor = MaterialTheme.appColors.warning
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    barChartData.forEach { bar ->
                        BarItem(
                            heightFraction = bar.heightFraction,
                            isOutlined = bar.isOutlined,
                            customColor = if (bar.isWarning) warningColor else null
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BarItem(
    heightFraction: Float,
    isOutlined: Boolean,
    customColor: Color? = null
) {
    val barColor = customColor ?: MaterialTheme.colorScheme.primary

    Box(
        modifier = Modifier
            .width(20.dp)
            .fillMaxSize(heightFraction)
            .background(
                color = if (isOutlined) MaterialTheme.appColors.softTint else barColor,
                shape = RoundedCornerShape(topStart = AppDimens.spacing4, topEnd = AppDimens.spacing4)
            )
            .then(
                if (isOutlined) Modifier.border(
                    width = 1.dp,
                    color = barColor,
                    shape = RoundedCornerShape(topStart = AppDimens.spacing4, topEnd = AppDimens.spacing4)
                ) else Modifier
            )
    )
}

@Composable
private fun RecentActivitySection(onSeeAllClick: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(AppDimens.spacing8)) {
        Text(
            text = "Aktivitas Logistik Terbaru",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(AppDimens.radiusCard),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.spacing2)
        ) {
            Column {
                ActivityTile(
                    title = "Barang Masuk (Inbound)",
                    subtitle = "PO-2023-1042 • Hub Jakarta Pusat",
                    status = "Selesai",
                    time = "10:42 AM",
                    statusColor = MaterialTheme.appColors.success,
                    statusContainer = MaterialTheme.appColors.successContainer,
                    icon = Icons.Default.CheckCircle
                )
                ActivityTile(
                    title = "Proses Pengiriman",
                    subtitle = "DO-2023-9921 • Ke Bandung",
                    status = "Transit",
                    time = "09:15 AM",
                    statusColor = MaterialTheme.appColors.warning,
                    statusContainer = MaterialTheme.appColors.warningContainer,
                    icon = Icons.Default.LocalShipping
                )
                ActivityTile(
                    title = "Peringatan Stok Rendah",
                    subtitle = "SKU-A928 (Palet Kayu) • Hub Surabaya",
                    status = "Kritis",
                    time = "Kemarin, 14:30",
                    statusColor = MaterialTheme.appColors.danger,
                    statusContainer = MaterialTheme.appColors.dangerContainer,
                    icon = Icons.Default.Error
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onSeeAllClick() }
                .padding(vertical = AppDimens.spacing8),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Lihat Semua Aktivitas",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun ActivityTile(
    title: String,
    subtitle: String,
    status: String,
    time: String,
    statusColor: Color,
    statusContainer: Color,
    icon: ImageVector
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppDimens.cardPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppDimens.spacing8)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(statusContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = statusColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column {
                Text(
                    text = title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(AppDimens.spacing2)
        ) {
            Box(
                modifier = Modifier
                    .background(statusContainer, RoundedCornerShape(AppDimens.radiusFull))
                    .padding(horizontal = AppDimens.spacing8, vertical = AppDimens.spacing2)
            ) {
                Text(
                    text = status,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = statusColor
                )
            }
            Text(
                text = time,
                fontSize = 9.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}