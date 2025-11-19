package com.CodeTrainer.codetrainer.domain.repository

import com.CodeTrainer.codetrainer.domain.model.HelpTopic
import kotlinx.coroutines.flow.Flow

interface HelpRepository {
    fun getAllHelpTopics(): Flow<List<HelpTopic>>
    fun getHelpTopicsByCategory(category: String): Flow<List<HelpTopic>>
    fun getHelpTopicById(id: Int): Flow<HelpTopic?>
    suspend fun initializeHelpData(): Boolean
    suspend fun getHelpTopicsCount(): Int
}
