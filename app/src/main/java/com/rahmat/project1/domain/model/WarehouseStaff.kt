package com.rahmat.project1.domain.model

data class WarehouseStaff(
    val id: String,
    val organizationId: String,
    val warehouseId: String,
    val userId: String,
    val role: String,
    val isActive: Boolean,
    val joinedAt: String?,
    val userName: String? = null,
    val warehouseName: String? = null
)