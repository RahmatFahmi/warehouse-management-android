package com.rahmat.project1.ui.features.admin.screen.master_data.warehouse

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rahmat.project1.ui.common.UiState
import com.rahmat.project1.ui.components.AppButton
import com.rahmat.project1.ui.components.AppMessageBanner
import com.rahmat.project1.ui.components.AppTextField
import com.rahmat.project1.ui.theme.AppDimens

@Composable
fun AddWarehouseScreen(
    onBackClick: () -> Unit = {},
    viewModel: WarehouseViewModel = hiltViewModel()
) {
    // Form States
    var name by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var maxCapacity by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(true) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val createState by viewModel.createState.collectAsState()
    val isFormValid = name.isNotBlank() && code.isNotBlank() && city.isNotBlank() && address.isNotBlank() && maxCapacity.isNotBlank()

    LaunchedEffect(createState) {
        if (createState is UiState.Success) {
            showSuccessDialog = true
        }
    }

    if (showSuccessDialog) {
        com.rahmat.project1.ui.components.AppSuccessDialog(
            title = "Gudang Berhasil Ditambahkan!",
            message = "Data gudang baru telah berhasil disimpan ke dalam sistem.",
            buttonText = "Kembali ke Master Data",
            onConfirm = {
                showSuccessDialog = false
                viewModel.resetCreateState()
                onBackClick()
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding() // Menjaga kontent dari Notch/Status Bar
                .padding(horizontal = AppDimens.screenPaddingHorizontal)
        ) {
            // Header Bar Perbaikan Alignment & Top Padding
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = AppDimens.spacing16),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.offset(x = (-12).dp) // Kompensasi internal padding bawaan IconButton
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                Text(
                    text = "Tambah Hub Gudang",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.offset(x = (-12).dp) // Mengatur teks agar mengikuti offset tombol back
                )
            }

            // Scrollable Form Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(AppDimens.spacing16)
            ) {
                Text(
                    text = "Lengkapi data di bawah untuk menambahkan fasilitas gudang/hub baru ke sistem.",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Input Nama Hub
                AppTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "Nama Hub / Gudang",
                    placeholder = "Misal: Hub Jakarta Pusat",
                    leadingIcon = Icons.Default.Business,
                    imeAction = ImeAction.Next
                )

                // Input Kode Hub
                AppTextField(
                    value = code,
                    onValueChange = { code = it.uppercase() },
                    label = "Kode Hub",
                    placeholder = "Misal: HUB-CGK-01",
                    leadingIcon = Icons.Default.Code,
                    imeAction = ImeAction.Next
                )

                // Input Kota
                AppTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = "Kota",
                    placeholder = "Misal: Jakarta Pusat / Surabaya",
                    leadingIcon = Icons.Default.LocationCity,
                    imeAction = ImeAction.Next
                )

                // Input Alamat Lengkap
                AppTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = "Alamat Lengkap",
                    placeholder = "Jl. Sudirman Kav 21, Jakarta Pusat...",
                    leadingIcon = Icons.Default.LocationOn,
                    imeAction = ImeAction.Next
                )

                // Input Maksimal Kapasitas
                AppTextField(
                    value = maxCapacity,
                    onValueChange = { input ->
                        if (input.all { it.isDigit() }) maxCapacity = input
                    },
                    label = "Maksimal Kapasitas (Unit / Item)",
                    placeholder = "Misal: 10000",
                    leadingIcon = Icons.Default.PieChart,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )

                // Toggle Status Keaktifan
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
                                text = "STATUS GUDANG",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(AppDimens.spacing4))
                            Text(
                                text = if (isActive) "Gudang Aktif & Beroperasi" else "Gudang Nonaktif",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
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

                AppMessageBanner(
                    message = if (createState is UiState.Error) (createState as UiState.Error).message else null
                )
            }

            // Bottom Action Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = AppDimens.spacing16)
            ) {
                AppButton(
                    text = "Simpan Hub Gudang",
                    trailingIcon = Icons.Default.Save,
                    enabled = isFormValid,
                    isLoading = createState is UiState.Loading,
                    onClick = {
                        viewModel.createWarehouse(
                            name = name,
                            code = code,
                            city = city,
                            address = address,
                            maxCapacity = maxCapacity.toIntOrNull() ?: 0
                        )
                    }
                )
            }
        }
    }
}