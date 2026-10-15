package com.rahmat.project1.data.usecase

import com.rahmat.project1.domain.model.WarehouseStaff
import com.rahmat.project1.domain.repository.WarehouseStaffRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetStaffStreamUseCase @Inject constructor(
    private val warehouseStaffRepository: WarehouseStaffRepository,
    private val getCurrentOrgId: GetCurrentOrganizationIdUseCase
) {
    suspend operator fun invoke(): Flow<List<WarehouseStaff>> = flow {
        val orgProfile = getCurrentOrgId()
        emitAll(warehouseStaffRepository.getStaffStream(orgProfile.organizationId))
    }
}
