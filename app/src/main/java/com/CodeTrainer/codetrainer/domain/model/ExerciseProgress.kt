package com.CodeTrainer.codetrainer.domain.model

data class ExerciseProgress(
    val attempts: Int,              // número de intentos
    val status: ProgressStatus,     // estado actual del ejercicio
    val lastAttemptAt: Long?        // timestamp en millis del último intento (puede ser null)
)
