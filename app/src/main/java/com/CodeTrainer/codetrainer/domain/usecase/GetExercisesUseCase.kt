package com.CodeTrainer.codetrainer.domain.usecase

import com.CodeTrainer.codetrainer.domain.model.ExerciseDetails
import com.CodeTrainer.codetrainer.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExercisesUseCase @Inject constructor(
    private val repository: ExerciseRepository // 1. Pedimos el "contrato", Hilt nos dará la Impl.
) {

    // 2. Usamos 'operator fun invoke' para que la clase se pueda llamar
    //    como si fuera una función. (ej. getExercisesUseCase())
    operator fun invoke(): Flow<List<ExerciseDetails>> {
        return repository.getAllExercises()
    }
}