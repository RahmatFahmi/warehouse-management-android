package com.rahmat.project1.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.WifiOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.rahmat.project1.ui.common.UiState
import com.rahmat.project1.ui.theme.AppDimens

/**
 * Reusable wrapper composable untuk menangani berbagai status UiState (Loading, Success, Empty, Error, NoInternet).
 * Menghindari penulisan `when (state)` berulang di setiap Screen.
 */
@Composable
fun <T> AppUiStateLayout(
    state: UiState<T>,
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null,
    loadingContent: @Composable () -> Unit = {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    },
    emptyContent: @Composable () -> Unit = {
        AppEmptyState(title = "Tidak ada data")
    },
    content: @Composable (T) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (state) {
            is UiState.Loading -> {
                loadingContent()
            }
            is UiState.NoInternet -> {
                AppEmptyState(
                    title = "Tidak Ada Koneksi Internet",
                    description = "Periksa jaringan Anda dan coba lagi.",
                    icon = Icons.Rounded.WifiOff,
                    actionLabel = if (onRetry != null) "Coba Lagi" else null,
                    onActionClick = onRetry ?: {}
                )
            }
            is UiState.Empty -> {
                emptyContent()
            }
            is UiState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(AppDimens.spacing24),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AppMessageBanner(message = state.message)
                    if (onRetry != null) {
                        Spacer(modifier = Modifier.height(AppDimens.spacing16))
                        Button(onClick = onRetry) {
                            Text("Coba Lagi")
                        }
                    }
                }
            }
            is UiState.Success -> {
                content(state.data)
            }
            else -> {}
        }
    }
}
