package com.rahmat.project1.data.repository

import com.rahmat.project1.data.mapper.toDomain
import com.rahmat.project1.data.mapper.toDto
import com.rahmat.project1.data.remote.dto.WarehouseHubDto
import com.rahmat.project1.domain.model.WarehouseHub
import com.rahmat.project1.domain.repository.WarehouseRepository
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WarehouseRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : WarehouseRepository {

    @OptIn(io.github.jan.supabase.annotations.SupabaseExperimental::class)
    override fun getWarehousesStream(organizationId: String): Flow<List<WarehouseHub>> {
        return postgrest.from("warehouse_hubs")
            .selectAsFlow(
                primaryKey = WarehouseHubDto::id,
                filter = {
                    eq("organization_id", organizationId)
                }
            )
            .map { list -> list.map { it.toDomain() } }
    }

    override suspend fun getWarehousesByOrganization(organizationId: String): List<WarehouseHub> {
        return postgrest.from("warehouse_hubs")
            .select {
                filter {
                    eq("organization_id", organizationId)
                }
            }
            .decodeList<WarehouseHubDto>()
            .map { it.toDomain() }
    }

    override suspend fun createWarehouse(warehouse: WarehouseHub) {
        postgrest.from("warehouse_hubs")
            .insert(warehouse.toDto())
    }

    override suspend fun updateWarehouse(warehouse: WarehouseHub) {
        val id = warehouse.id
        postgrest.from("warehouse_hubs")
            .update(warehouse.toDto()) {
                filter {
                    eq("id", id)
                }
            }
    }

    override suspend fun deleteWarehouse(id: String) {
        postgrest.from("warehouse_hubs")
            .delete {
                filter {
                    eq("id", id)
                }
            }
    }
}
