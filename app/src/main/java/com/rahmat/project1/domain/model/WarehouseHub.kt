package com.rahmat.project1.domain.model

data class WarehouseHub(
    val id: String,
    val organizationId: String,
    val name: String,
    val code: String,
    val city: String,
    val address: String,
    val isActive: Boolean,
    val maxCapacity: Int,
    val usedCapacity: Int,
    val staffCount: Int = 0,
    val createdAt: String? = null,
    val updatedAt: String? = null
)
