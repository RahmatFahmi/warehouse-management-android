package com.rahmat.project1.common.network

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {
    val isConnected: Flow<Boolean>
}