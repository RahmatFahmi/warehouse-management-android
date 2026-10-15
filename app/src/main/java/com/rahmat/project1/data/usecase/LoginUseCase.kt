package com.rahmat.project1.data.usecase

import com.rahmat.project1.domain.repository.AuthRepository
import com.rahmat.project1.domain.repository.ProfileRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(email: String, password: String): String {
        // 1. Lakukan Sign In
        authRepository.signIn(email, password)

        // 2. Ambil User ID yang sedang aktif
        val userId = authRepository.getCurrentUserId()
            ?: throw Exception("Gagal mendapatkan User ID setelah login")

        // 3. Ambil data profil dari database untuk mendapatkan Role
        val profile = profileRepository.getProfile(userId)
            ?: throw Exception("Profil pengguna tidak ditemukan di database")

        // 4. Kembalikan data Role
        return profile.roleType
    }
}
