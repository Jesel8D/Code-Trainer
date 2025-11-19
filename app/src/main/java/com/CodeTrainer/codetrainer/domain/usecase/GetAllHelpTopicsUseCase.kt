package com.CodeTrainer.codetrainer.domain.usecase

import com.CodeTrainer.codetrainer.domain.model.HelpTopic
import com.CodeTrainer.codetrainer.domain.repository.HelpRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllHelpTopicsUseCase @Inject constructor(
    private val repository: HelpRepository
) {
    operator fun invoke(): Flow<List<HelpTopic>> = repository.getAllHelpTopics()
}
