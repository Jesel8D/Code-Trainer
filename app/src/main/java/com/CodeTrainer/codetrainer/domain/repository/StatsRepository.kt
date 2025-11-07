package com.CodeTrainer.codetrainer.domain.repository

import com.CodeTrainer.codetrainer.domain.model.Stats // (Crearemos este modelo)
import kotlinx.coroutines.flow.Flow

interface StatsRepository {
    fun getStats(): Flow<Stats>
    suspend fun updateStats(stats: Stats)
}