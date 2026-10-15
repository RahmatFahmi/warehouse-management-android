package com.rahmat.project1.domain.model

data class Profile(
    val id: String,
    val organizationId: String,
    val fullName: String,
    val roleType: String,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val isActive: Boolean = true
)
