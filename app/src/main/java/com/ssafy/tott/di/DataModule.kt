package com.ssafy.tott.di

import com.ssafy.tott.data.datasource.BuildingDataSource
import com.ssafy.tott.data.datasource.remote.BuildingRemoteDataSource
import com.ssafy.tott.data.repository.BuildingRepositoryImpl
import com.ssafy.tott.domain.repository.SearchBuildingRepository
import com.ssafy.tott.data.datasource.AccountDataSource
import com.ssafy.tott.data.datasource.remote.AccountDataSourceRemote
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
    fun provideSearchBuildingRepository(buildingDataSource: BuildingDataSource): SearchBuildingRepository =
        BuildingRepositoryImpl(buildingDataSource)

    @Provides
    @Singleton
    fun provideSearchBuildingDataSource(): BuildingDataSource =
        BuildingRemoteDataSource()

    @Provides
    @Singleton
    fun provideAccountDataSource(): AccountDataSource =
        AccountDataSourceRemote()
}