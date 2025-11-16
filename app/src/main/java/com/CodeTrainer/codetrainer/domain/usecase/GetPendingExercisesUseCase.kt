package com.CodeTrainer.codetrainer.domain.usecase

import com.CodeTrainer.codetrainer.domain.model.ExerciseDetails
import com.CodeTrainer.codetrainer.domain.model.ProgressStatus
import com.CodeTrainer.codetrainer.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPendingExercisesUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    operator fun invoke(): Flow<List<ExerciseDetails>> {
        // Obtenemos todos los ejercicios y los filtramos
        return repository.getAllExercises().map { exercises ->
            exercises.filter {
                // Un ejercicio está "pendiente" si no tiene progreso O si su progreso es PENDING
                it.progress == null || it.progress.status == ProgressStatus.PENDING
            }
        }
    }
}