package com.CodeTrainer.codetrainer.data.repository

import com.CodeTrainer.codetrainer.data.local.dao.StatsDao
import com.CodeTrainer.codetrainer.domain.model.Stats
import com.CodeTrainer.codetrainer.domain.repository.StatsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StatsRepositoryImpl @Inject constructor(
    private val statsDao: StatsDao
) : StatsRepository {

    override fun getStats(): Flow<Stats> {
        // Si es nulo, creamos stats por defecto
        return statsDao.getStats().map { it?.toDomain() ?: Stats(0, 0L, 0.0, 0) }
    }

    override suspend fun updateStats(stats: Stats) {
        statsDao.upsertStats(stats.toEntity())
    }
}