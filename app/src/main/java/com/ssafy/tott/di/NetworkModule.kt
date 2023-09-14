package com.ssafy.tott.di

import android.util.Log
import com.google.maps.android.v3.ktx.BuildConfig
import com.ssafy.tott.data.datasource.local.DataStoreManager
import com.ssafy.tott.data.datasource.remote.service.UserService
import com.ssafy.tott.data.model.response.AuthTokenRemoteResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "http://www.tott.site:8080"

    @Provides
    fun provideBaseUrl() = BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(
        authAuthenticator: Authenticator,
    ) = if (BuildConfig.DEBUG) {
        OkHttpClient.Builder()
            .authenticator(authAuthenticator)
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()
    } else {
        OkHttpClient.Builder().authenticator(authAuthenticator).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(provideBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): SearchBuildingService {
        return retrofit.create(SearchBuildingService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Singleton
    @Provides
    fun provideTokenAuthenticator(
        dataStoreManager: DataStoreManager,
    ) = Authenticator { _, response ->
        runBlocking {
            val newTokenResponse = getNewToken(dataStoreManager.refreshToken.first())

            if (!newTokenResponse.isSuccessful || newTokenResponse.body() == null) { //Couldn't refresh the token, so restart the login process
                // TODO Refresh가 불가능하면 Home 화면으로 이동
                Log.d("NetworkModule", "provideTokenAuthenticator: refresh fail")
            }

            newTokenResponse.body()?.let {
                dataStoreManager.setAccessToken(it.accessToken)
                dataStoreManager.setRefreshToken(it.refreshToken)
                response.request.newBuilder()
                    .header("Authorization", it.accessToken)
                    .build()
            }
        }
    }

    private suspend fun getNewToken(refreshToken: String?): Response<AuthTokenRemoteResponse> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val service = retrofit.create(UserService::class.java)
        return service.refreshToken(refreshToken ?: "")
    }
}