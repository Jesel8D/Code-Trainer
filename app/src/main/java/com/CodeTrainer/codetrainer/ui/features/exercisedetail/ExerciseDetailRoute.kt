package com.CodeTrainer.codetrainer.ui.features.exercisedetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ExerciseDetailRoute(
    viewModel: ExerciseDetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    ExerciseDetailScreen(
        uiState = uiState,
        onNavigateBack = onNavigateBack,
        onSolutionChange = { viewModel.onSolutionChange(it) },
        onToggleHint = { viewModel.toggleHint() },
        onToggleSolution = { viewModel.toggleSolution() },
        onSubmit = { viewModel.submitSolution() },
        onClearError = { viewModel.clearError() },
        onClearSuccess = { viewModel.clearSuccessMessage() }
    )
}
