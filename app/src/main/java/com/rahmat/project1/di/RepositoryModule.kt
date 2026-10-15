package com.rahmat.project1.di

import com.rahmat.project1.data.repository.AuthRepositoryImpl
import com.rahmat.project1.data.repository.OrganizationRepositoryImpl
import com.rahmat.project1.data.repository.ProfileRepositoryImpl
import com.rahmat.project1.data.repository.WarehouseRepositoryImpl
import com.rahmat.project1.domain.repository.AuthRepository
import com.rahmat.project1.domain.repository.OrganizationRepository
import com.rahmat.project1.domain.repository.ProfileRepository
import com.rahmat.project1.domain.repository.WarehouseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindOrganizationRepository(
        organizationRepositoryImpl: OrganizationRepositoryImpl
    ): OrganizationRepository

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        profileRepositoryImpl: ProfileRepositoryImpl
    ): ProfileRepository

    @Binds
    @Singleton
    abstract fun bindWarehouseRepository(
        warehouseRepositoryImpl: WarehouseRepositoryImpl
    ): WarehouseRepository

    @Binds
    @Singleton
    abstract fun bindWarehouseStaffRepository(
        warehouseStaffRepositoryImpl: com.rahmat.project1.data.repository.WarehouseStaffRepositoryImpl
    ): com.rahmat.project1.domain.repository.WarehouseStaffRepository

}
