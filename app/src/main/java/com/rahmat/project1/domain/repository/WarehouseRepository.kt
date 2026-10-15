package com.rahmat.project1.domain.repository

import com.rahmat.project1.domain.model.WarehouseHub
import kotlinx.coroutines.flow.Flow

interface WarehouseRepository {
    fun getWarehousesStream(organizationId: String): Flow<List<WarehouseHub>>
    suspend fun getWarehousesByOrganization(organizationId: String): List<WarehouseHub>
    suspend fun createWarehouse(warehouse: WarehouseHub)
    suspend fun updateWarehouse(warehouse: WarehouseHub)
    suspend fun deleteWarehouse(id: String)
}
