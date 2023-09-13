package com.ssafy.tott.di

import com.ssafy.tott.data.datasource.UserDataSource
import com.ssafy.tott.data.datasource.BuildingDataSource
import com.ssafy.tott.data.datasource.remote.UserDataSourceRemote
import com.ssafy.tott.data.datasource.remote.BuildingRemoteDataSource
import com.ssafy.tott.data.repository.BuildingRepositoryImpl
import com.ssafy.tott.data.repository.UserRepositoryImpl
import com.ssafy.tott.domain.repository.SearchBuildingRepository
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
    fun provideUserRepository(regisDataSource: UserDataSource): UserRepository =
        UserRepositoryImpl(regisDataSource)

    @Provides
    @Singleton
    fun provideAccountDataSource(loginService: LoginService): UserDataSource =
        UserDataSourceRemote(loginService)

    @Provides
    @Singleton
    fun provideSearchBuildingRepository(buildingDataSource: BuildingDataSource): SearchBuildingRepository =
        BuildingRepositoryImpl(buildingDataSource)

    @Provides
    @Singleton
    fun provideSearchBuildingDataSource(buildingService: SearchBuildingService): BuildingDataSource =
        BuildingRemoteDataSource(buildingService)
}