package com.CodeTrainer.codetrainer.ui.features.settings

import com.CodeTrainer.codetrainer.domain.model.UserPreferences

data class SettingsUiState(
    val isLoading: Boolean = true,
    val preferences: UserPreferences = UserPreferences(),
    val helpTopicsCount: Int = 0,
    val isInitializingHelp: Boolean = false,
    val initializationSuccess: Boolean = false,
    val error: String? = null
)
