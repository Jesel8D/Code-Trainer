package com.CodeTrainer.codetrainer.ui.features.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DashboardRoute(
    viewModel: DashboardViewModel = hiltViewModel(),
    onExerciseClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    DashboardScreen(
        uiState = uiState,
        onExerciseClick = onExerciseClick,
        onRefresh = { viewModel.refresh() },
        onRefreshQuote = { viewModel.refreshQuote() }
    )
}
