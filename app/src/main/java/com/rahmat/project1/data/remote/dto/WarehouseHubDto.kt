package com.rahmat.project1.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WarehouseHubDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("organization_id")
    val organizationId: String,
    @SerialName("name")
    val name: String,
    @SerialName("code")
    val code: String,
    @SerialName("city")
    val city: String = "",
    @SerialName("address")
    val address: String,
    @SerialName("is_active")
    val isActive: Boolean = true,
    @SerialName("max_capacity")
    val maxCapacity: Int,
    @SerialName("used_capacity")
    val usedCapacity: Int = 0,
    @SerialName("staff_count")
    val staffCount: Int = 0,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null
)
