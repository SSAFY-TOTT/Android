package com.ssafy.tott.di

import com.ssafy.tott.data.datasource.BuildingDataSource
import com.ssafy.tott.data.datasource.remote.UserRemoteDataSource
import com.ssafy.tott.data.datasource.local.DataStoreManager
import com.ssafy.tott.data.datasource.local.UserTokenDataSource
import com.ssafy.tott.data.datasource.local.UserTokenDataSourceImpl
import com.ssafy.tott.data.datasource.remote.BuildingRemoteDataSource
import com.ssafy.tott.data.datasource.remote.RegionRemoteDataSource
import com.ssafy.tott.data.datasource.remote.RegionRemoteDataSourceImpl
import com.ssafy.tott.data.datasource.remote.UserRemoteRemoteDataSourceImpl
import com.ssafy.tott.data.datasource.remote.service.BuildingService
import com.ssafy.tott.data.datasource.remote.service.RegionService
import com.ssafy.tott.data.datasource.remote.service.UserService
import com.ssafy.tott.data.repository.BuildingRepositoryImpl
import com.ssafy.tott.data.repository.RegionRepositoryImpl
import com.ssafy.tott.data.repository.UserRepositoryImpl
import com.ssafy.tott.domain.repository.BuildingRepository
import com.ssafy.tott.domain.repository.RegionRepository
import com.ssafy.tott.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideUserRepository(
        userRemoteDataSource: UserRemoteDataSource,
        userTokenDataSource: UserTokenDataSource,
    ): UserRepository =
        UserRepositoryImpl(userRemoteDataSource, userTokenDataSource)

    @Provides
    @Singleton
    fun provideAccountDataSource(userService: UserService): UserRemoteDataSource =
        UserRemoteRemoteDataSourceImpl(userService)

    @Provides
    @Singleton
    fun provideSearchBuildingRepository(buildingDataSource: BuildingDataSource): BuildingRepository =
        BuildingRepositoryImpl(buildingDataSource)

    @Provides
    @Singleton
    fun provideSearchBuildingDataSource(buildingService: BuildingService): BuildingDataSource =
        BuildingRemoteDataSource(buildingService)

    @Provides
    @Singleton
    fun provideUserTokenDataSource(dataStoreManager: DataStoreManager): UserTokenDataSource =
        UserTokenDataSourceImpl(dataStoreManager)


    @Provides
    @Singleton
    fun provideRegionDataSource(regionService: RegionService): RegionRemoteDataSource =
        RegionRemoteDataSourceImpl(regionService)


    @Provides
    @Singleton
    fun provideRegionRepository(regionDataSource: RegionRemoteDataSource): RegionRepository =
        RegionRepositoryImpl(regionDataSource)
}