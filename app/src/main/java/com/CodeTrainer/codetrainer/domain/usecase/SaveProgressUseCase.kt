package com.CodeTrainer.codetrainer.domain.usecase

import com.CodeTrainer.codetrainer.domain.model.Progress
import com.CodeTrainer.codetrainer.domain.repository.ExerciseRepository
import javax.inject.Inject

class SaveProgressUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    suspend operator fun invoke(progress: Progress) {
        repository.saveProgress(progress)
    }
}
