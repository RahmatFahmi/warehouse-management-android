package com.rahmat.project1.ui.features.intro.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rahmat.project1.R
import com.rahmat.project1.ui.components.AppButton
import com.rahmat.project1.ui.theme.AppDimens
import kotlinx.coroutines.launch

data class IntroPage(
    val title: String,
    val description: String,
    val iconRes: Int
)

val introPages = listOf(
    IntroPage(
        title = "Efisiensi Logistik",
        description = "Optimalkan alur pengiriman barang Anda dengan sistem manajemen yang cerdas dan terintegrasi.",
        iconRes = R.drawable.ic_intro_delivery
    ),
    IntroPage(
        title = "Kelola Banyak Hub",
        description = "Pantau dan kendalikan berbagai gudang atau hub pusat Anda dalam satu genggaman aplikasi.",
        iconRes = R.drawable.ic_intro_warehouse
    ),
    IntroPage(
        title = "Pelacakan Akurat",
        description = "Kelola ribuan SKU dan pantau pergerakan stok secara detail menggunakan fitur pemindaian barcode.",
        iconRes = R.drawable.ic_intro_inventory
    ),
    IntroPage(
        title = "Sinkronisasi Real-time",
        description = "Data selalu diperbarui secara instan untuk seluruh tim, memastikan operasional berjalan tanpa hambatan.",
        iconRes = R.drawable.ic_intro_realtime
    )
)

@Composable
fun IntroScreen(
    onFinish: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { introPages.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Tombol Skip (Hanya muncul jika bukan di halaman terakhir)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppDimens.spacing16),
            contentAlignment = Alignment.TopEnd
        ) {
            if (pagerState.currentPage < introPages.size - 1) {
                TextButton(onClick = onFinish) {
                    Text(
                        text = "Lewati",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Pager Area
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { position ->
            IntroPageContent(introPages[position])
        }

        // Bottom Controls
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppDimens.spacing24)
                .padding(bottom = AppDimens.spacing40),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Page Indicator
            Row(
                Modifier
                    .height(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(introPages.size) { iteration ->
                    val color = if (pagerState.currentPage == iteration) 
                        MaterialTheme.colorScheme.primary 
                    else 
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    
                    val width = if (pagerState.currentPage == iteration) 24.dp else 8.dp
                    
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .clip(RoundedCornerShape(AppDimens.radiusFull))
                            .background(color)
                            .width(width)
                            .fillMaxHeight()
                    )
                }
            }

            Spacer(modifier = Modifier.height(AppDimens.spacing32))

            // Action Button
            AnimatedVisibility(
                visible = pagerState.currentPage == introPages.size - 1,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                AppButton(
                    text = "Mulai Sekarang",
                    onClick = onFinish
                )
            }

            if (pagerState.currentPage < introPages.size - 1) {
                AppButton(
                    text = "Selanjutnya",
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun IntroPageContent(page: IntroPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = AppDimens.spacing32),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = page.iconRes),
            contentDescription = null,
            modifier = Modifier.size(240.dp)
        )
        
        Spacer(modifier = Modifier.height(AppDimens.spacing48))
        
        Text(
            text = page.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(AppDimens.spacing16))
        
        Text(
            text = page.description,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )
    }
}
