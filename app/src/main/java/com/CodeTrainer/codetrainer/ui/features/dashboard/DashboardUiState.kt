package com.CodeTrainer.codetrainer.ui.features.dashboard

import com.CodeTrainer.codetrainer.domain.model.ExerciseWithProgress
import com.CodeTrainer.codetrainer.domain.model.Quote
import com.CodeTrainer.codetrainer.domain.model.Stats
import com.CodeTrainer.codetrainer.domain.model.Tip

data class DashboardUiState(
    val isLoading: Boolean = true,
    val userName: String = "",
    val stats: Stats = Stats(
        totalExercisesCompleted = 0,
        totalTimeSpent = 0L,
        averageScore = 0.0,
        dailyStreak = 0
    ),
    val recentExercises: List<ExerciseWithProgress> = emptyList(),
    val dailyTip: Tip? = null,
    val dailyQuote: Quote? = null,
    val isLoadingQuote: Boolean = false,
    val quoteError: String? = null,
    val error: String? = null
)