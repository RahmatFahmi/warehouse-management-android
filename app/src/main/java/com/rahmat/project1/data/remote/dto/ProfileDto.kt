package com.rahmat.project1.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileDto(
    @SerialName("id")
    val id: String,
    @SerialName("organization_id")
    val organizationId: String,
    @SerialName("full_name")
    val fullName: String,
    @SerialName("role_type")
    val roleType: String,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null,
    @SerialName("is_active")
    val isActive: Boolean = true
)
