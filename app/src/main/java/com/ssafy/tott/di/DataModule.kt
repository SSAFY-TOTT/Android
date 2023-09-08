package com.ssafy.tott.di

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
    fun provideAccountDataSource(): AccountDataSource =
        AccountDataSourceRemote()
}