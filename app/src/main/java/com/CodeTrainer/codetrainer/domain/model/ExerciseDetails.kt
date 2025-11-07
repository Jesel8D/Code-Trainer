package com.CodeTrainer.codetrainer.domain.model

// Este es el modelo que la UI usará la mayor parte del tiempo.
// Combina el ejercicio y su progreso (que puede ser nulo).
data class ExerciseDetails(
    val exercise: Exercise,
    val progress: Progress?
)