package com.rahmat.project1.ui.features.admin.screen.master_data.sku

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.FitScreen
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rahmat.project1.ui.components.AppButton
import com.rahmat.project1.ui.components.AppTextField
import com.rahmat.project1.ui.theme.AppDimens
import com.rahmat.project1.ui.theme.appColors
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AddSkuScreen(
    onBackClick: () -> Unit = {},
    onSubmitClick: (
        code: String,
        name: String,
        category: String,
        dimensions: String,
        weight: String,
        imageUrl: String
    ) -> Unit = { _, _, _, _, _, _ -> }
) {
    val scope = rememberCoroutineScope()

    var code by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var dimensions by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val isFormValid = code.isNotBlank() && name.isNotBlank()

    if (showSuccessDialog) {
        com.rahmat.project1.ui.components.AppSuccessDialog(
            title = "SKU Berhasil Disimpan!",
            message = "Produk baru $name ($code) telah berhasil ditambahkan ke katalog.",
            buttonText = "Kembali ke Master Data",
            onConfirm = {
                showSuccessDialog = false
                onSubmitClick(code, name, category, dimensions, weight, imageUrl)
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
                .statusBarsPadding()
                .padding(horizontal = AppDimens.screenPaddingHorizontal)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = AppDimens.spacing16),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.offset(x = (-12).dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                Text(
                    text = "Input SKU Baru",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.offset(x = (-12).dp)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(AppDimens.spacing16)
            ) {
                Text(
                    text = "Lengkapi detail barang untuk menambahkan SKU baru ke dalam katalog.",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(AppDimens.radiusCard))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.appColors.border,
                            shape = RoundedCornerShape(AppDimens.radiusCard)
                        )
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(AppDimens.spacing8)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddPhotoAlternate,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        )
                        Text(
                            text = "Unggah Foto Produk",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Format JPG, PNG (Maks 2MB)",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                AppTextField(
                    value = code,
                    onValueChange = { code = it },
                    label = "Kode SKU",
                    placeholder = "Misal: LNV-T14-001",
                    leadingIcon = Icons.Default.Label,
                    imeAction = ImeAction.Next
                )

                AppTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "Nama Produk",
                    placeholder = "Misal: ThinkPad T14 Gen 3",
                    leadingIcon = Icons.Default.Inventory2,
                    imeAction = ImeAction.Next
                )

                AppTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = "Kategori Produk",
                    placeholder = "Misal: Elektronik / Aksesoris",
                    leadingIcon = Icons.Default.Category,
                    imeAction = ImeAction.Next
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppDimens.spacing16)
                ) {
                    AppTextField(
                        value = dimensions,
                        onValueChange = { dimensions = it },
                        label = "Dimensi (PxLxT)",
                        placeholder = "317 × 227 × 17.9 mm",
                        leadingIcon = Icons.Default.FitScreen,
                        modifier = Modifier.weight(1f),
                        imeAction = ImeAction.Next
                    )

                    AppTextField(
                        value = weight,
                        onValueChange = { weight = it },
                        label = "Berat",
                        placeholder = "1.32 kg",
                        leadingIcon = Icons.Default.Scale,
                        modifier = Modifier.weight(1f),
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )
                }

                AppTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = "URL Gambar (Opsional)",
                    placeholder = "https://example.com/image.png",
                    leadingIcon = Icons.Default.Image,
                    imeAction = ImeAction.Done
                )

                Spacer(modifier = Modifier.height(AppDimens.spacing24))
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = AppDimens.spacing16)
            ) {
                AppButton(
                    text = "Simpan SKU",
                    trailingIcon = Icons.Default.Add,
                    isLoading = isLoading,
                    onClick = {
                        if (isFormValid) {
                            scope.launch {
                                isLoading = true
                                delay(1000)
                                isLoading = false
                                showSuccessDialog = true
                            }
                        }
                    }
                )
            }
        }
    }
}