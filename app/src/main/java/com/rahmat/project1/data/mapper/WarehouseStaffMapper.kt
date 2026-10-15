package com.rahmat.project1.data.mapper

import com.rahmat.project1.data.remote.dto.WarehouseStaffDto
import com.rahmat.project1.domain.model.WarehouseStaff

fun WarehouseStaffDto.toDomain(): WarehouseStaff {
    return WarehouseStaff(
        id = id ?: "",
        organizationId = organizationId,
        warehouseId = warehouseId,
        userId = userId,
        role = role,
        isActive = isActive,
        joinedAt = joinedAt,
        userName = userName,
        warehouseName = warehouseName
    )
}

fun WarehouseStaff.toDto(): WarehouseStaffDto {
    return WarehouseStaffDto(
        id = id.ifBlank { null },
        organizationId = organizationId,
        warehouseId = warehouseId,
        userId = userId,
        role = role,
        isActive = isActive,
        joinedAt = joinedAt
    )
}