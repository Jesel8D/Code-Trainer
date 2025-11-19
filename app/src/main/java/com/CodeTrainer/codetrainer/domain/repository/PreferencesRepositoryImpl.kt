package com.CodeTrainer.codetrainer.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.CodeTrainer.codetrainer.domain.model.UserPreferences
import com.CodeTrainer.codetrainer.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferencesRepository {

    companion object {
        private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        private val LANGUAGE_KEY = stringPreferencesKey("language")
        private val DAILY_REMINDERS_KEY = booleanPreferencesKey("daily_reminders")
    }

    override fun getUserPreferences(): Flow<UserPreferences> {
        return dataStore.data.map { preferences ->
            UserPreferences(
                isDarkMode = preferences[DARK_MODE_KEY] ?: false,
                language = preferences[LANGUAGE_KEY] ?: "es",
                dailyRemindersEnabled = preferences[DAILY_REMINDERS_KEY] ?: false
            )
        }
    }

    override suspend fun updateDarkMode(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = enabled
        }
    }

    override suspend fun updateLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language
        }
    }

    override suspend fun updateDailyReminders(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[DAILY_REMINDERS_KEY] = enabled
        }
    }
}