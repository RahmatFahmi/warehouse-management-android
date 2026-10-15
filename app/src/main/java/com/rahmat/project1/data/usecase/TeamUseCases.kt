package com.rahmat.project1.data.usecase

import com.rahmat.project1.domain.repository.AuthRepository
import com.rahmat.project1.domain.repository.ProfileRepository
import javax.inject.Inject

class CreateStaffUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val getCurrentOrgId: GetCurrentOrganizationIdUseCase
) {
    suspend operator fun invoke(
        fullName: String,
        email: String,
        roleType: String,
        password: String,
        wareHouseId: String,
        isActive: Boolean = true
    ) {
        val orgId = getCurrentOrgId()

        authRepository.createStaffAccount(
            email = email,
            password = password,
            fullName = fullName,
            roleType = roleType,
            organizationId = orgId.organizationId,
            warehouseId = wareHouseId,
            isActive = isActive
        )
    }
}