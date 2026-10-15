package com.rahmat.project1.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.rahmat.project1.ui.theme.AppDimens
import com.rahmat.project1.ui.theme.appColors
import kotlinx.coroutines.launch

/**
 * Component Umpan Balik Sukses Standar Android (Dialog / Modal)
 *
 * Digunakan setelah proses/aksi berhasil (seperti Registrasi, Tambah Data, Simpan Perubahan)
 * sebelum pengguna kembali ke halaman sebelumnya atau melanjutkan ke halaman berikutnya.
 *
 * @param title Judul pemberitahuan (cth: "Registrasi Berhasil!")
 * @param message Detail penjelasan atau instruksi selanjutnya
 * @param buttonText Teks pada tombol konfirmasi (cth: "Lanjut ke Login", "Kembali")
 * @param icon Icon sukses yang ditampilkan di atas pesan
 * @param onConfirm Action ketika tombol utama diklik
 * @param onDismiss Action ketika dialog ditutup melalui tombol back/klik di luar
 */
@Composable
fun AppSuccessDialog(
    title: String,
    message: String,
    buttonText: String = "Lanjutkan",
    icon: ImageVector = Icons.Rounded.CheckCircle,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit = onConfirm
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = scaleIn(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
            exit = scaleOut()
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.88f)
                    .clip(RoundedCornerShape(AppDimens.radiusCard)),
                shape = RoundedCornerShape(AppDimens.radiusCard),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = AppDimens.spacing8
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppDimens.spacing24),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Badge Icon Sukses dengan background soft/tint
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.appColors.successContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = "Sukses",
                            tint = MaterialTheme.appColors.success,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(AppDimens.spacing16))

                    // Judul
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(AppDimens.spacing8))

                    // Deskripsi
                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(AppDimens.spacing24))

                    // Tombol Aksi Utama
                    AppButton(
                        text = buttonText,
                        onClick = onConfirm,
                        trailingIcon = null
                    )
                }
            }
        }
    }
}

/**
 * Variasi Bottom Sheet untuk feedback sukses (Alternatif Dialog)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSuccessBottomSheet(
    title: String,
    message: String,
    buttonText: String = "Lanjutkan",
    icon: ImageVector = Icons.Rounded.CheckCircle,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit = onConfirm
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(
            topStart = AppDimens.radiusCard,
            topEnd = AppDimens.radiusCard
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppDimens.spacing24)
                .padding(bottom = AppDimens.spacing32, top = AppDimens.spacing8),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.appColors.successContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Sukses",
                    tint = MaterialTheme.appColors.success,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(AppDimens.spacing16))

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(AppDimens.spacing8))

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(AppDimens.spacing24))

            AppButton(
                text = buttonText,
                onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        onConfirm()
                    }
                },
                trailingIcon = null
            )
        }
    }
}
