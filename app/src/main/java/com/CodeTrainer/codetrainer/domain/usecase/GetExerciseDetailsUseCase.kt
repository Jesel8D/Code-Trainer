package com.CodeTrainer.codetrainer.domain.usecase

import com.CodeTrainer.codetrainer.domain.model.ExerciseDetails
import com.CodeTrainer.codetrainer.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExerciseDetailsUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    operator fun invoke(exerciseId: Int): Flow<ExerciseDetails?> {
        return repository.getExerciseDetails(exerciseId)
    }
}
