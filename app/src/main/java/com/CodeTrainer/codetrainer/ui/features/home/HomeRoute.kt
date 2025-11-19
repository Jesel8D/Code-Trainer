package com.CodeTrainer.codetrainer.ui.features.home

import androidx.compose.runtime.Composable

@Composable
fun HomeRoute(
    onNavigateToExerciseDetail: (Int) -> Unit,
    onNavigateToHelp: () -> Unit,
    onLogout: () -> Unit
) {
    HomeScreen(
        onNavigateToExerciseDetail = onNavigateToExerciseDetail,
        onNavigateToHelp = onNavigateToHelp,
        onLogout = onLogout
    )
}
