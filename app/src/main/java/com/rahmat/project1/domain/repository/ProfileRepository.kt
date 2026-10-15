package com.rahmat.project1.domain.repository

import com.rahmat.project1.domain.model.Profile

interface ProfileRepository {
    suspend fun createProfile(
        userId: String,
        organizationId: String,
        fullName: String,
        roleType: String,
        isActive: Boolean = true
    )
    suspend fun getProfile(userId: String): Profile?
}