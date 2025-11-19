package com.CodeTrainer.codetrainer.domain.usecase

import com.CodeTrainer.codetrainer.domain.model.Stats
import com.CodeTrainer.codetrainer.domain.repository.StatsRepository
import javax.inject.Inject

class UpdateStatsUseCase @Inject constructor(
    private val repository: StatsRepository
) {
    suspend operator fun invoke(stats: Stats) {
        repository.updateStats(stats)
    }
}
