package com.CodeTrainer.codetrainer.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.CodeTrainer.codetrainer.data.local.entity.HelpTopicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HelpTopicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHelpTopics(topics: List<HelpTopicEntity>)

    @Query("SELECT * FROM help_topics ORDER BY category, topicOrder")
    fun getAllHelpTopics(): Flow<List<HelpTopicEntity>>

    @Query("SELECT * FROM help_topics WHERE category = :category ORDER BY topicOrder")
    fun getHelpTopicsByCategory(category: String): Flow<List<HelpTopicEntity>>

    @Query("SELECT * FROM help_topics WHERE id = :id")
    fun getHelpTopicById(id: Int): Flow<HelpTopicEntity?>

    @Query("SELECT COUNT(*) FROM help_topics")
    suspend fun getHelpTopicsCount(): Int
}
