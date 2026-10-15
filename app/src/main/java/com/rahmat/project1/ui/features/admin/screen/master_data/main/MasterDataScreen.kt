package com.rahmat.project1.ui.features.admin.screen.master_data.main

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warehouse
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rahmat.project1.domain.model.WarehouseHub
import com.rahmat.project1.ui.common.UiState
import com.rahmat.project1.ui.components.AppEmptyState
import com.rahmat.project1.ui.components.AppMessageBanner
import com.rahmat.project1.ui.components.WarehouseCardSkeleton
import com.rahmat.project1.ui.components.shimmerEffect
import com.rahmat.project1.ui.theme.AppDimens
import com.rahmat.project1.ui.theme.appColors

data class SkuItem(
    val id: String,
    val code: String,
    val name: String,
    val category: String,
    val dimensions: String,
    val weight: String,
    val stock: Int,
    val hubName: String,
    val imageUrl: String? = null
)

private val masterDataTabs = listOf("Data Gudang", "Katalog SKU")

@Composable
fun MasterDataScreen(
    onAddWarehouseClick: () -> Unit = {},
    onAddSkuClick: () -> Unit = {},
    onWarehouseClick: (WarehouseHub) -> Unit = {},
    onSkuClick: (SkuItem) -> Unit = {},
    onNotificationClick: () -> Unit = {},
    viewModel: MasterDataViewModel = hiltViewModel()
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val warehousesState by viewModel.warehousesState.collectAsState()

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
                        contentDescription = "Logo FlowShip",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "FlowShip",
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
                            contentDescription = "Notifikasi",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(AppDimens.spacing16))

            Text(
                text = "Master Data",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Kelola data gudang dan katalog SKU Anda.",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(AppDimens.spacing16))

            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            ) {
                masterDataTabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = title,
                                fontSize = 14.sp,
                                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Medium,
                                color = if (selectedTabIndex == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(AppDimens.spacing16))

            when (selectedTabIndex) {
                0 -> WarehouseTabContent(
                    state = warehousesState,
                    onWarehouseClick = onWarehouseClick,
                    onAddWarehouseClick = onAddWarehouseClick,
                    onRetry = { viewModel.refresh() }
                )
                1 -> SkuTabContent(
                    onSkuClick = onSkuClick,
                    onAddSkuClick = onAddSkuClick
                )
            }
        }
    }
}

@Composable
private fun WarehouseTabContent(
    state: UiState<List<WarehouseHub>>,
    onWarehouseClick: (WarehouseHub) -> Unit,
    onAddWarehouseClick: () -> Unit,
    onRetry: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (state) {
            is UiState.Loading -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(AppDimens.spacing16),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(5) {
                        WarehouseCardSkeleton()
                    }
                }
            }
            is UiState.Empty -> {
                AppEmptyState(
                    title = "Belum ada data gudang",
                    description = "Gudang yang ditambahkan akan muncul di sini.",
                    icon = Icons.Default.Warehouse,
                    actionLabel = "Tambah Hub Gudang",
                    onActionClick = onAddWarehouseClick,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is UiState.Error -> {
                AppEmptyState(
                    title = state.message,
                    description = "Silahkan Refresh Ulang",
                    icon = Icons.Default.Error,
                    actionLabel = "Coba lagi",
                    onActionClick = onRetry,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is UiState.Success -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(AppDimens.spacing16),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.data, key = { it.id }) { warehouse ->
                        WarehouseCard(warehouse = warehouse, onClick = { onWarehouseClick(warehouse) })
                    }
                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
            else -> {}
        }

        Button(
            onClick = onAddWarehouseClick,
            shape = RoundedCornerShape(AppDimens.radiusCard),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = AppDimens.spacing24)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Hub")
            Spacer(modifier = Modifier.width(AppDimens.spacing8))
            Text(text = "Hub Gudang Baru", fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
    }
}

@Composable
private fun WarehouseCard(
    warehouse: WarehouseHub,
    onClick: () -> Unit
) {
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
                Column {
                    Text(text = warehouse.name, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    Text(text = warehouse.code, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                Box(
                    modifier = Modifier
                        .background(MaterialTheme.appColors.successContainer, RoundedCornerShape(AppDimens.radiusFull))
                        .padding(horizontal = AppDimens.spacing8, vertical = AppDimens.spacing4)
                ) {
                    Text(
                        text = if (warehouse.isActive) "Aktif" else "Nonaktif",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.appColors.success
                    )
                }
            }

            val capacityUsedPercentage = remember(warehouse.usedCapacity, warehouse.maxCapacity) {
                if (warehouse.maxCapacity > 0) (warehouse.usedCapacity * 100) / warehouse.maxCapacity else 0
            }

            Column(verticalArrangement = Arrangement.spacedBy(AppDimens.spacing4)) {
                if (warehouse.city.isNotBlank()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.LocationCity, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.width(AppDimens.spacing8))
                        Text(text = warehouse.city, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.width(AppDimens.spacing8))
                    Text(text = warehouse.address, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Group, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.width(AppDimens.spacing8))
                    Text(text = "${warehouse.staffCount} Staff Operational", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Inventory2, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.width(AppDimens.spacing8))
                    Text(text = "Kapasitas: $capacityUsedPercentage% Terpakai (${warehouse.usedCapacity} / ${warehouse.maxCapacity} Unit)", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

private val skuCategories = listOf("Semua", "Elektronik", "Aksesoris", "Display")

@Composable
private fun SkuTabContent(
    onSkuClick: (SkuItem) -> Unit,
    onAddSkuClick: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Semua") }

    val skuList = remember {
        listOf(
            SkuItem("1", "LNV-T14-001", "ThinkPad T14 Gen 3", "Elektronik", "317 × 227 × 17.9 mm", "1.32 kg", 120, "Hub JKT", "gambar1"),
            SkuItem("2", "LOG-MX3-BLK", "Logitech MX Master 3S", "Aksesoris", "124 × 84 × 51 mm", "141 g", 450, "Hub JKT", "gambar2"),
            SkuItem("3", "DEL-U2723QE", "Dell UltraSharp 27\" 4K", "Display", "611 × 385 × 185 mm", "6.64 kg", 85, "Hub SBY", "gambar3")
        )
    }

    val filteredList by remember(searchQuery, selectedCategory) {
        derivedStateOf {
            skuList.filter { sku ->
                val matchesSearch = sku.name.contains(searchQuery, ignoreCase = true) || sku.code.contains(searchQuery, ignoreCase = true)
                val matchesCategory = if (selectedCategory == "Semua") true else sku.category.equals(selectedCategory, ignoreCase = true)
                matchesSearch && matchesCategory
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Cari SKU atau nama barang...", fontSize = 12.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
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

            LazyRow(horizontalArrangement = Arrangement.spacedBy(AppDimens.spacing8)) {
                items(skuCategories, key = { it }) { cat ->
                    val isSelected = cat == selectedCategory
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(AppDimens.radiusFull))
                            .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
                            .then(if (!isSelected) Modifier.border(1.dp, MaterialTheme.appColors.border, RoundedCornerShape(AppDimens.radiusFull)) else Modifier)
                            .clickable { selectedCategory = cat }
                            .padding(horizontal = AppDimens.spacing16, vertical = AppDimens.spacing8)
                    ) {
                        Text(
                            text = cat,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(AppDimens.spacing16))

            if (filteredList.isEmpty()) {
                AppEmptyState(
                    title = "Tidak ada data SKU",
                    description = "Coba atur ulang kata kunci pencarian atau pilih kategori lain.",
                    icon = Icons.Default.Inventory2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = AppDimens.spacing32)
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(AppDimens.spacing16),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredList, key = { it.id }) { sku ->
                        SkuCard(sku = sku, onClick = { onSkuClick(sku) })
                    }
                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        }

        Button(
            onClick = onAddSkuClick,
            shape = RoundedCornerShape(AppDimens.radiusCard),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = AppDimens.spacing24)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah SKU")
            Spacer(modifier = Modifier.width(AppDimens.spacing8))
            Text(text = "Input SKU Baru", fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
    }
}


@Composable
private fun SkuCard(
    sku: SkuItem,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val imageResId = remember(sku.imageUrl) {
        sku.imageUrl?.let { name ->
            context.resources.getIdentifier(name, "drawable", context.packageName)
                .takeIf { it != 0 }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
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
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                if (imageResId != null) {
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = sku.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.BrokenImage,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(AppDimens.iconSizeSmall)
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(AppDimens.spacing8)) {
                    Text(text = sku.code, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(text = sku.category, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }

                Text(text = sku.name, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text(text = "${sku.dimensions} • ${sku.weight}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                Spacer(modifier = Modifier.height(AppDimens.spacing8))

                Box(
                    modifier = Modifier
                        .background(MaterialTheme.appColors.successContainer, RoundedCornerShape(AppDimens.radiusFull))
                        .padding(horizontal = AppDimens.spacing8, vertical = AppDimens.spacing4)
                ) {
                    Text(
                        text = "${sku.stock} Unit • ${sku.hubName}",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.appColors.success
                    )
                }
            }

            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More", tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}