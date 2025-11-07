package com.CodeTrainer.codetrainer.domain.repository

import com.CodeTrainer.codetrainer.domain.model.Tip // (Crearemos este modelo)
import kotlinx.coroutines.flow.Flow

interface TipRepository {
    fun getAllTips(): Flow<List<Tip>>
    suspend fun getRandomTip(): Tip?
}