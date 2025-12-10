package com.CodeTrainer.codetrainer.data.repository

import com.CodeTrainer.codetrainer.domain.repository.PreferencesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesRepositoryImpl @Inject constructor(
    // Inyecta DataStore / SharedPreferences cuando quieras usarlas
) : PreferencesRepository
