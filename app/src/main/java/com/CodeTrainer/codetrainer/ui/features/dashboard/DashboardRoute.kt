package com.CodeTrainer.codetrainer.ui.features.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DashboardRoute(
    viewModel: DashboardViewModel = hiltViewModel(),
    onNavigateToExercises: () -> Unit,
    onNavigateToHelp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    DashboardScreen(
        uiState = uiState,
        onNavigateToExercises = onNavigateToExercises,
        onNavigateToHelp = onNavigateToHelp,
        onRefresh = { viewModel.refreshDashboard() }
    )
}