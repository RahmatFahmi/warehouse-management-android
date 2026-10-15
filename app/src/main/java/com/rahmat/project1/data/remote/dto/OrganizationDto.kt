package com.rahmat.project1.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrganizationDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("email")
    val email: String,
    @SerialName("created_at")
    val createdAt: String? = null
)
