package com.ssafy.tott.di

import com.ssafy.tott.data.datasource.BuildingDataSource
import com.ssafy.tott.data.datasource.UserDataSource
import com.ssafy.tott.data.datasource.local.DataStoreManager
import com.ssafy.tott.data.datasource.local.UserTokenDataSource
import com.ssafy.tott.data.datasource.local.UserTokenDataSourceImpl
import com.ssafy.tott.data.datasource.remote.BuildingRemoteDataSource
import com.ssafy.tott.data.datasource.remote.UserDataSourceRemote
import com.ssafy.tott.data.datasource.remote.service.BuildingService
import com.ssafy.tott.data.datasource.remote.service.UserService
import com.ssafy.tott.data.repository.BuildingRepositoryImpl
import com.ssafy.tott.data.repository.UserRepositoryImpl
import com.ssafy.tott.domain.repository.BuildingRepository
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
        userDataSource: UserDataSource,
        userTokenDataSource: UserTokenDataSource,
    ): UserRepository =
        UserRepositoryImpl(userDataSource, userTokenDataSource)

    @Provides
    @Singleton
    fun provideAccountDataSource(userService: UserService): UserDataSource =
        UserDataSourceRemote(userService)

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
}