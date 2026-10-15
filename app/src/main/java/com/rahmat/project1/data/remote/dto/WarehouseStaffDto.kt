package com.rahmat.project1.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WarehouseStaffDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("organization_id")
    val organizationId: String,
    @SerialName("warehouse_id")
    val warehouseId: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("role")
    val role: String = "operational",
    @SerialName("is_active")
    val isActive: Boolean = true,
    @SerialName("joined_at")
    val joinedAt: String? = null,

    // Properti opsional (misal hasil JOIN dari tabel profiles atau warehouse_hubs)
    @SerialName("user_name")
    val userName: String? = null,
    @SerialName("warehouse_name")
    val warehouseName: String? = null
)