package com.rahmat.project1.domain.repository

interface AuthRepository {
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(
        email: String,
        password: String,
        companyName: String = "",
        ownerName: String = ""
    ): String
    suspend fun createStaffAccount(
        email: String,
        password: String,
        fullName: String = "",
        roleType: String = "",
        organizationId: String = "",
        warehouseId: String = "",
        isActive: Boolean = true
    ): String
    suspend fun signOut()
    fun getCurrentUserId(): String?
}