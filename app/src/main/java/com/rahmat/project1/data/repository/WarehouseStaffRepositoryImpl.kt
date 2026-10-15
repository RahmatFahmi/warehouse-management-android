package com.rahmat.project1.data.repository

import com.rahmat.project1.data.mapper.toDomain
import com.rahmat.project1.data.mapper.toDto
import com.rahmat.project1.data.remote.dto.ProfileDto
import com.rahmat.project1.data.remote.dto.WarehouseHubDto
import com.rahmat.project1.data.remote.dto.WarehouseStaffDto
import com.rahmat.project1.domain.model.WarehouseStaff
import com.rahmat.project1.domain.repository.WarehouseStaffRepository
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WarehouseStaffRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : WarehouseStaffRepository {

    override suspend fun createWarehouseStaff(staff: WarehouseStaff) {
        postgrest.from("warehouse_staff")
            .insert(staff.toDto())
    }

    override suspend fun getStaffWarehouses(userId: String): List<String> {
        return postgrest.from("warehouse_staff")
            .select {
                filter {
                    eq("user_id", userId)
                    eq("is_active", true)
                }
            }
            .decodeList<WarehouseStaffDto>()
            .map { it.warehouseId }
    }

    @OptIn(io.github.jan.supabase.annotations.SupabaseExperimental::class)
    override fun getStaffStream(organizationId: String): Flow<List<WarehouseStaff>> {
        return postgrest.from("warehouse_staff")
            .selectAsFlow(
                primaryKey = WarehouseStaffDto::id,
                filter = {
                    eq("organization_id", organizationId)
                }
            )
            .map { staffDtos ->
                val profilesMap = try {
                    postgrest.from("profiles")
                        .select { filter { eq("organization_id", organizationId) } }
                        .decodeList<ProfileDto>()
                        .associateBy { it.id }
                } catch (e: Exception) {
                    android.util.Log.e("WarehouseStaffRepo", "Gagal fetch profiles: ${e.message}", e)
                    emptyMap()
                }

                val warehousesMap = try {
                    postgrest.from("warehouse_hubs")
                        .select { filter { eq("organization_id", organizationId) } }
                        .decodeList<WarehouseHubDto>()
                        .associateBy { it.id }
                } catch (e: Exception) {
                    android.util.Log.e("WarehouseStaffRepo", "Gagal fetch warehouse_hubs: ${e.message}", e)
                    emptyMap()
                }

                staffDtos.map { dto ->
                    val profile = profilesMap[dto.userId]
                    val warehouse = warehousesMap[dto.warehouseId]
                    dto.toDomain().copy(
                        userName = profile?.fullName ?: dto.userName ?: "Staf",
                        warehouseName = warehouse?.name ?: dto.warehouseName ?: "Gudang"
                    )
                }
            }
    }
}
