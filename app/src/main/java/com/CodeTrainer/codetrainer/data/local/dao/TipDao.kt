package com.CodeTrainer.codetrainer.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.CodeTrainer.codetrainer.data.local.entity.TipEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TipDao {

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insertTips(tips: List<TipEntity>)

    @Query("SELECT * FROM tips")
    fun getAllTips(): Flow<List<TipEntity>>

    // Para la "ayuda rápida" o un consejo en el dashboard
    @Query("SELECT * FROM tips ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomTip(): TipEntity?
}