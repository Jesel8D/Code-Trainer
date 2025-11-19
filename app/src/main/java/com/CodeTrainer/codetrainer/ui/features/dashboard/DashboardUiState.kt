package com.CodeTrainer.codetrainer.ui.features.dashboard

import com.CodeTrainer.codetrainer.domain.model.ExerciseDetails
import com.CodeTrainer.codetrainer.domain.model.Stats
import com.CodeTrainer.codetrainer.domain.model.Tip

data class DashboardUiState(
    val isLoading: Boolean = true,
    val userName: String = "",
    val userEmail: String = "",
    val stats: Stats = Stats(
        totalExercisesCompleted = 0,
        totalTimeSpent = 0L,
        averageScore = 0.0,
        dailyStreak = 0
    ),
    val weeklyProgress: Int = 0, // Ejercicios completados esta semana
    val pendingExercises: List<ExerciseDetails> = emptyList(),
    val dailyTip: Tip? = null,
    val error: String? = null
)
