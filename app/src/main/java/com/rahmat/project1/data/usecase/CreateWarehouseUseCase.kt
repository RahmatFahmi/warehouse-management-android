package com.rahmat.project1.data.usecase

import com.rahmat.project1.domain.model.WarehouseHub
import com.rahmat.project1.domain.repository.WarehouseRepository
import javax.inject.Inject

class CreateWarehouseUseCase @Inject constructor(
    private val warehouseRepository: WarehouseRepository,
    private val getCurrentOrgId: GetCurrentOrganizationIdUseCase
) {
    suspend operator fun invoke(
        name: String,
        code: String,
        city: String,
        address: String,
        maxCapacity: Int
    ) {
        val orgId = getCurrentOrgId()
        
        val warehouse = WarehouseHub(
            id = "",
            organizationId = orgId.organizationId,
            name = name,
            code = code,
            city = city,
            address = address,
            isActive = true,
            maxCapacity = maxCapacity,
            usedCapacity = 0,
            staffCount = 0
        )
        warehouseRepository.createWarehouse(warehouse)
    }
}
