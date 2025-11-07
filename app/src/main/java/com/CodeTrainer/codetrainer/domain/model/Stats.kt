package com.CodeTrainer.codetrainer.domain.model

data class Stats(
    val totalExercisesCompleted: Int,
    val totalTimeSpent: Long,
    val averageScore: Double,
    val dailyStreak: Int
)