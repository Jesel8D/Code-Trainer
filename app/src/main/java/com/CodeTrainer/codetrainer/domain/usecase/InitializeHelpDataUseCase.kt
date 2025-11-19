package com.CodeTrainer.codetrainer.domain.usecase

import com.CodeTrainer.codetrainer.domain.repository.HelpRepository
import javax.inject.Inject

class InitializeHelpDataUseCase @Inject constructor(
    private val repository: HelpRepository
) {
    suspend operator fun invoke(): Boolean = repository.initializeHelpData()
}