package com.rahmat.project1.domain.repository

interface OrganizationRepository {
    suspend fun createOrganization(name: String, email: String): String
}