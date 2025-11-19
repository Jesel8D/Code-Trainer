package com.CodeTrainer.codetrainer.ui.features.exerciselist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ExerciseListRoute(
    viewModel: ExerciseListViewModel = hiltViewModel(),
    onNavigateToDetail: (Int) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    ExerciseListScreen(
        uiState = uiState,
        onExerciseClick = onNavigateToDetail,
        onLanguageFilterChange = { language -> viewModel.setLanguageFilter(language) },
        onLevelFilterChange = { level -> viewModel.setLevelFilter(level) },
        onClearFilters = { viewModel.clearFilters() }
    )
}
