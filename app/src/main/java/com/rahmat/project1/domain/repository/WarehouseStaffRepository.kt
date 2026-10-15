package com.rahmat.project1.domain.repository

import com.rahmat.project1.domain.model.WarehouseStaff
import kotlinx.coroutines.flow.Flow

interface WarehouseStaffRepository {
    suspend fun createWarehouseStaff(staff: WarehouseStaff)
    suspend fun getStaffWarehouses(userId: String): List<String>
    fun getStaffStream(organizationId: String): Flow<List<WarehouseStaff>>
}
