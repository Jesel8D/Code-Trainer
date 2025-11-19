package com.CodeTrainer.codetrainer.data.repository

import com.CodeTrainer.codetrainer.data.local.DatabaseSeeder
import com.CodeTrainer.codetrainer.data.local.dao.HelpTopicDao
import com.CodeTrainer.codetrainer.domain.model.HelpTopic
import com.CodeTrainer.codetrainer.domain.repository.HelpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HelpRepositoryImpl @Inject constructor(
    private val helpTopicDao: HelpTopicDao
) : HelpRepository {

    override fun getAllHelpTopics(): Flow<List<HelpTopic>> {
        return helpTopicDao.getAllHelpTopics().map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getHelpTopicsByCategory(category: String): Flow<List<HelpTopic>> {
        return helpTopicDao.getHelpTopicsByCategory(category).map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getHelpTopicById(id: Int): Flow<HelpTopic?> {
        return helpTopicDao.getHelpTopicById(id).map { it?.toDomain() }
    }

    override suspend fun initializeHelpData(): Boolean {
        return try {
            // Verificar si ya hay datos
            val count = helpTopicDao.getHelpTopicsCount()
            if (count == 0) {
                // Insertar help topics desde el seeder
                val helpTopics = DatabaseSeeder.getHelpTopics()
                helpTopicDao.insertHelpTopics(helpTopics)
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getHelpTopicsCount(): Int {
        return helpTopicDao.getHelpTopicsCount()
    }
}
