package com.CodeTrainer.codetrainer.ui.features.help

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HelpDetailRoute(
    viewModel: HelpDetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    HelpDetailScreen(
        uiState = uiState,
        onNavigateBack = onNavigateBack
    )
}
