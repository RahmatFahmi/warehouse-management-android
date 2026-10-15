package com.rahmat.project1.data.usecase

import com.rahmat.project1.domain.model.WarehouseHub
import com.rahmat.project1.domain.repository.AuthRepository
import com.rahmat.project1.domain.repository.ProfileRepository
import com.rahmat.project1.domain.repository.WarehouseRepository
import com.rahmat.project1.domain.repository.WarehouseStaffRepository
import javax.inject.Inject

class GetAvailableWarehousesUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository,
    private val warehouseRepository: WarehouseRepository,
    private val warehouseStaffRepository: WarehouseStaffRepository
) {
    suspend operator fun invoke(): List<WarehouseHub> {
        // 1. Ambil ID akun user yang sedang login
        val currentUserId = authRepository.getCurrentUserId()
            ?: throw Exception("Sesi berakhir, silakan login kembali")

        // 2. Ambil profil user untuk mengetahui role dan organizationId
        val profile = profileRepository.getProfile(currentUserId)
            ?: throw Exception("Profil tidak ditemukan")

        val organizationId = profile.organizationId

        // 3. Logika pemisahan hak akses berdasarkan Peran (Role)
        return when (profile.roleType.lowercase()) {
            "admin" -> {
                // Jika Admin: Ambil semua gudang dalam organisasi tersebut
                warehouseRepository.getWarehousesByOrganization(organizationId)
            }
            "manager" -> {
                // Jika Manager: 
                // a. Ambil daftar warehouseId di mana manager ini ditugaskan
                val assignedWarehouseIds = warehouseStaffRepository.getStaffWarehouses(currentUserId)
                
                // b. Ambil semua gudang organisasi, lalu filter yang id-nya terdaftar untuk manager tersebut
                warehouseRepository.getWarehousesByOrganization(organizationId).filter { warehouse ->
                    assignedWarehouseIds.contains(warehouse.id)
                }
            }
            else -> {
                // Peran lain (misal staff biasa) tidak memiliki akses melihat daftar gudang untuk input
                emptyList()
            }
        }
    }
}
