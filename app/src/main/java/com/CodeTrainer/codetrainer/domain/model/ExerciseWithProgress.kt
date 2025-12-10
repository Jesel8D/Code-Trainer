package com.CodeTrainer.codetrainer.domain.model

data class ExerciseWithProgress(
    val exercise: Exercise,            // tu modelo existente de Exercise
    val progress: ExerciseProgress?    // tu modelo de progreso (puede ser null)
)

