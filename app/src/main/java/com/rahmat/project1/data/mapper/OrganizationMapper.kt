package com.rahmat.project1.data.mapper

import com.rahmat.project1.data.remote.dto.OrganizationDto
import com.rahmat.project1.domain.model.Organization

fun OrganizationDto.toDomain(): Organization {
    return Organization(
        id = id,
        name = name,
        email = email,
        createdAt = createdAt
    )
}
