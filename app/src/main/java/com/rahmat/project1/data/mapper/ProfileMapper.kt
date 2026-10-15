package com.rahmat.project1.data.mapper

import com.rahmat.project1.data.remote.dto.ProfileDto
import com.rahmat.project1.domain.model.Profile

fun ProfileDto.toDomain(): Profile {
    return Profile(
        id = id,
        organizationId = organizationId,
        fullName = fullName,
        roleType = roleType,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isActive = isActive
    )
}
