package com.rahmat.project1.data.usecase

import com.rahmat.project1.domain.model.Profile
import com.rahmat.project1.domain.repository.AuthRepository
import com.rahmat.project1.domain.repository.ProfileRepository
import javax.inject.Inject

class GetCurrentOrganizationIdUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(): Profile {
        val userId = authRepository.getCurrentUserId()
            ?: throw Exception("Sesi berakhir, silakan login kembali")
        val profile = profileRepository.getProfile(userId)
            ?: throw Exception("Profil tidak ditemukan")
        return profile
    }
}