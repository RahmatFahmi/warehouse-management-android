package com.rahmat.project1.data.repository

import com.rahmat.project1.domain.repository.AuthRepository
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: Auth
) : AuthRepository {
    override suspend fun signIn(email: String, password: String) {
        auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
    }

    override suspend fun signUp(
        email: String,
        password: String,
        companyName: String,
        ownerName: String
    ): String {
        val userInfo = auth.signUpWith(Email) {
            this.email = email
            this.password = password
            data = buildJsonObject {
                put("company_name", companyName)
                put("owner_name", ownerName)
                put("is_staff", false)
            }
        }
        return userInfo?.id ?: error("Gagal membuat akun")
    }

    override suspend fun createStaffAccount(
        email: String,
        password: String,
        fullName: String,
        roleType: String,
        organizationId: String,
        warehouseId: String,
        isActive: Boolean
    ): String {
        // 1. Simpan session admin dulu
        val adminSession = auth.currentSessionOrNull()
            ?: error("Sesi admin tidak ditemukan")

        return try {
            // 2. Buat akun staff baru dengan metadata
            val userInfo = auth.signUpWith(Email) {
                this.email = email
                this.password = password
                data = buildJsonObject {
                    put("full_name", fullName)
                    put("role_type", roleType)
                    put("organization_id", organizationId)
                    put("warehouse_id", warehouseId)
                    put("is_active", isActive)
                    put("is_staff", true)
                }
            }
            val newUserId = userInfo?.id ?: error("Gagal membuat akun staff")

            newUserId
        } finally {
            // 3. Restore session admin, apapun yang terjadi
            auth.importSession(adminSession)
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override fun getCurrentUserId(): String? {
        return auth.currentUserOrNull()?.id
    }
}
