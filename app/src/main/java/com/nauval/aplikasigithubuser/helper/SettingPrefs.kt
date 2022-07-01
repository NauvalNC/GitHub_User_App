package com.nauval.aplikasigithubuser.helper

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPrefs private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveThemeMode(isDarkMode: Boolean) {
        dataStore.edit { pref -> pref[booleanPreferencesKey(THEME_PREF_NAME)] = isDarkMode }
    }

    fun getIsDarkMode(): Flow<Boolean> {
        return dataStore.data.map { pref -> pref[booleanPreferencesKey(THEME_PREF_NAME)] ?: false }
    }

    companion object {
        const val PREF_NAME = "settings"
        private const val THEME_PREF_NAME = "theme_mode"

        @Volatile
        private var INSTANCE: SettingPrefs? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPrefs {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPrefs(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}