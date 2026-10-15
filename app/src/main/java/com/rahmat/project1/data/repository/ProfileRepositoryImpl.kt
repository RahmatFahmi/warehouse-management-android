package com.rahmat.project1.data.repository

import com.rahmat.project1.data.mapper.toDomain
import com.rahmat.project1.data.remote.dto.OrganizationDto
import com.rahmat.project1.data.remote.dto.ProfileDto
import com.rahmat.project1.domain.model.Profile
import com.rahmat.project1.domain.repository.ProfileRepository
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : ProfileRepository {

    override suspend fun createProfile(
        userId: String,
        organizationId: String,
        fullName: String,
        roleType: String,
        isActive: Boolean
    ){
        postgrest.from("profiles")
            .insert(
                ProfileDto(
                    id = userId,
                    organizationId = organizationId,
                    fullName = fullName,
                    roleType = roleType,
                    isActive = isActive
                )
            )
    }

    override suspend fun getProfile(userId: String): Profile? {
        return postgrest.from("profiles")
            .select {
                filter {
                    eq("id", userId)
                }
            }
            .decodeSingleOrNull<ProfileDto>()
            ?.toDomain()
    }
}
