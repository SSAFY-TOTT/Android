package com.ssafy.tott.di

import android.util.Log
import com.google.maps.android.v3.ktx.BuildConfig
import com.ssafy.tott.data.datasource.local.DataStoreManager
import com.ssafy.tott.data.datasource.remote.service.BuildingService
import com.ssafy.tott.data.datasource.remote.service.RegionService
import com.ssafy.tott.data.datasource.remote.service.UserService
import com.ssafy.tott.data.model.response.AuthTokenRemoteResponse
import com.ssafy.tott.domain.exception.NetworkException
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "http://www.tott.site:8080"

    @AuthOkHttpClient
    @Singleton
    @Provides
    fun provideAuthOkHttpClient(
        authAuthenticator: Authenticator,
        dataStoreManager: DataStoreManager,
    ) = if (BuildConfig.DEBUG) {
        OkHttpClient.Builder().authenticator(authAuthenticator).addInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        ).addNetworkInterceptor(AppInterceptor(dataStoreManager)).build()
    } else {
        OkHttpClient.Builder().authenticator(authAuthenticator)
            .addNetworkInterceptor(AppInterceptor(dataStoreManager)).build()
    }

    @OtherOkHttpClient
    @Singleton
    @Provides
    fun provideOtherOkHttpClient(
    ) = if (BuildConfig.DEBUG) {
        OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        ).build()
    } else {
        OkHttpClient.Builder().build()
    }

    class AppInterceptor(
        private val dataStoreManager: DataStoreManager
    ) : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response = with(chain) {
            runBlocking {
                val accessToken = dataStoreManager.accessToken.first() ?: throw NetworkException(
                    null, "토큰이 없습니다."
                )
                Log.d("NetworkModule", "intercept: accessToken $accessToken")
                val newRequest = request().newBuilder()
                    .addHeader("Authorization", accessToken) // 헤더에 authorization라는 key로 JWT 를 넣어준다.
                    .build()
                proceed(newRequest)
            }
        }
    }

    @AuthRetrofit
    @Singleton
    @Provides
    fun provideAuthRetrofit(@AuthOkHttpClient okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().client(okHttpClient).baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @OtherRetrofit
    @Singleton
    @Provides
    fun provideOtherRetrofit(@OtherOkHttpClient okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().client(okHttpClient).baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideBuildingApiService(@AuthRetrofit retrofit: Retrofit): BuildingService {
        return retrofit.create(BuildingService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApiService(@OtherRetrofit retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideRegionApiService(@AuthRetrofit retrofit: Retrofit): RegionService {
        return retrofit.create(RegionService::class.java)
    }

    @Singleton
    @Provides
    fun provideTokenAuthenticator(
        dataStoreManager: DataStoreManager,
        userService: UserService,
    ) = Authenticator { _, response ->
        runBlocking {
            val newTokenResponse = getNewToken(dataStoreManager.refreshToken.first(), userService)

            if (!newTokenResponse.isSuccessful || newTokenResponse.body() == null) { //Couldn't refresh the token, so restart the login process
                // TODO Refresh가 불가능하면 Home 화면으로 이동
                Log.d("NetworkModule", "provideTokenAuthenticator: refresh fail")
            }

            newTokenResponse.body()?.let {
                dataStoreManager.setAccessToken(it.accessToken)
                dataStoreManager.setRefreshToken(it.refreshToken)
                response.request.newBuilder().header("Authorization", it.accessToken).build()
            }
        }
    }

    private suspend fun getNewToken(
        refreshToken: String?,
        userService: UserService,
    ): Response<AuthTokenRemoteResponse> {
        return userService.refreshToken(refreshToken ?: "")
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class OtherOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class OtherRetrofit
}