package com.ssafy.tott.data.datasource.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(private val dataStore: DataStore<Preferences>) {
    suspend fun setAccessToken(token: String) {
        dataStore.edit { settings ->
            settings[ACCESS_TOKEN] = token
        }
    }

    val accessToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[ACCESS_TOKEN]
    }

    suspend fun setRefreshToken(token: String) {
        dataStore.edit { settings ->
            settings[REFRESH_TOKEN] = token
        }
    }

    val refreshToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[REFRESH_TOKEN]
    }

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
        private val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")
    }
}