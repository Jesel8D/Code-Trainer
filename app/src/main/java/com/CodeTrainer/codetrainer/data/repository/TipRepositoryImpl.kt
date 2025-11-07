package com.CodeTrainer.codetrainer.data.repository

import com.CodeTrainer.codetrainer.data.local.dao.TipDao
import com.CodeTrainer.codetrainer.domain.model.Tip
import com.CodeTrainer.codetrainer.domain.repository.TipRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TipRepositoryImpl @Inject constructor(
    private val tipDao: TipDao
) : TipRepository {

    override fun getAllTips(): Flow<List<Tip>> {
        return tipDao.getAllTips().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getRandomTip(): Tip? {
        return tipDao.getRandomTip()?.toDomain()
    }
}