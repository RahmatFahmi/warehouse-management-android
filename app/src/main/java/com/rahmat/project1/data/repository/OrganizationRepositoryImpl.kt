package com.rahmat.project1.data.repository

import com.rahmat.project1.data.remote.dto.OrganizationDto
import com.rahmat.project1.domain.repository.OrganizationRepository
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Inject

class OrganizationRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : OrganizationRepository {

    override suspend fun createOrganization(name: String, email: String): String {
        val result = postgrest.from("organizations")
            .insert(mapOf(
                "name" to name,
                "email" to email
            )) {
                select()
            }
            .decodeSingle<OrganizationDto>()
        return result.id
    }
}
