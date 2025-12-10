package com.CodeTrainer.codetrainer.ui.features.profile

import com.CodeTrainer.codetrainer.domain.model.Stats

data class ProfileUiState(
    val isLoading: Boolean = true,
    val userName: String = "",
    val userEmail: String = "",
    val userId: String = "",
    val profileImageUrl: String? = null,
    val isUploadingImage: Boolean = false,
    val stats: Stats = Stats(
        totalExercisesCompleted = 0,
        totalTimeSpent = 0L,
        averageScore = 0.0,
        dailyStreak = 0
    ),
    val mostPracticedLanguage: String = "Ninguno",
    val pythonCompleted: Int = 0,
    val cppCompleted: Int = 0,
    val basicCompleted: Int = 0,
    val intermediateCompleted: Int = 0,
    val advancedCompleted: Int = 0,
    val isLoggingOut: Boolean = false,
    val error: String? = null
)