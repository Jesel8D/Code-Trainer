package com.CodeTrainer.codetrainer.domain.repository

import com.CodeTrainer.codetrainer.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    fun getUserPreferences(): Flow<UserPreferences>
    suspend fun updateDarkMode(enabled: Boolean)
    suspend fun updateLanguage(language: String)
    suspend fun updateDailyReminders(enabled: Boolean)
}
