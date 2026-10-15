package com.rahmat.project1.data.usecase

import com.rahmat.project1.domain.model.WarehouseHub
import com.rahmat.project1.domain.repository.WarehouseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWarehousesStreamUseCase @Inject constructor(
    private val warehouseRepository: WarehouseRepository,
    private val getCurrentOrgId: GetCurrentOrganizationIdUseCase
) {
    suspend operator fun invoke(): Flow<List<WarehouseHub>> = flow {
        val orgId = getCurrentOrgId()
        emitAll(warehouseRepository.getWarehousesStream(orgId.organizationId))
    }
}