package com.CodeTrainer.codetrainer.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.CodeTrainer.codetrainer.data.local.entity.StatsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StatsDao {

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun upsertStats(stats: StatsEntity)

    // Obtenemos la única fila (id = 1) y la observamos con Flow
    @Query("SELECT * FROM stats WHERE id = 1")
    fun getStats(): Flow<StatsEntity?>
}