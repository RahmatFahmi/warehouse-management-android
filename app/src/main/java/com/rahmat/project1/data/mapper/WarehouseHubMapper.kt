package com.rahmat.project1.data.mapper

import com.rahmat.project1.data.remote.dto.WarehouseHubDto
import com.rahmat.project1.domain.model.WarehouseHub

fun WarehouseHubDto.toDomain(): WarehouseHub {
    return WarehouseHub(
        id = id ?: "",
        organizationId = organizationId,
        name = name,
        code = code,
        city = city,
        address = address,
        isActive = isActive,
        maxCapacity = maxCapacity,
        usedCapacity = usedCapacity,
        staffCount = staffCount,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun WarehouseHub.toDto(): WarehouseHubDto {
    return WarehouseHubDto(
        id = id.ifBlank { null },
        organizationId = organizationId,
        name = name,
        code = code,
        city = city,
        address = address,
        isActive = isActive,
        maxCapacity = maxCapacity,
        usedCapacity = usedCapacity
    )
}
