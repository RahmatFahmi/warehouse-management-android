package com.rahmat.project1.common.network

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState

@Composable
fun connectivityState(networkMonitor: NetworkMonitor): State<Boolean> {
    return produceState(initialValue = true) {
        networkMonitor.isConnected.collect { value = it }
    }
}