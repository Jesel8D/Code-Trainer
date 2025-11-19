package com.CodeTrainer.codetrainer.ui.features.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingsRoute(
    viewModel: SettingsViewModel = hiltViewModel(),
    onNavigateToHelp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    SettingsScreen(
        uiState = uiState,
        onNavigateToHelp = onNavigateToHelp,
        onDarkModeToggle = { viewModel.toggleDarkMode(it) },
        onLanguageChange = { viewModel.updateLanguage(it) },
        onDailyRemindersToggle = { viewModel.toggleDailyReminders(it) },
        onInitializeHelp = { viewModel.initializeHelpData() },
        onClearError = { viewModel.clearError() },
        onClearSuccess = { viewModel.clearSuccessMessage() }
    )
}
