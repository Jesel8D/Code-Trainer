package com.CodeTrainer.codetrainer.ui.features.help

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HelpRoute(
    viewModel: HelpViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onTopicClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    HelpScreen(
        uiState = uiState,
        onNavigateBack = onNavigateBack,
        onTopicClick = onTopicClick,
        onRefresh = { viewModel.refresh() }
    )
}
