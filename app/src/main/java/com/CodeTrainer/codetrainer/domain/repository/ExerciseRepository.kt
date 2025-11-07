package com.CodeTrainer.codetrainer.domain.repository

import com.CodeTrainer.codetrainer.domain.model.ExerciseDetails
import com.CodeTrainer.codetrainer.domain.model.Progress
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {

    // Obtener la lista para el dashboard/historial
    fun getAllExercises(): Flow<List<ExerciseDetails>>

    // Obtener un ejercicio específico para la pantalla de "resolver"
    fun getExerciseDetails(id: Int): Flow<ExerciseDetails?>

    // Guardar el progreso del usuario
    suspend fun saveProgress(progress: Progress)
}