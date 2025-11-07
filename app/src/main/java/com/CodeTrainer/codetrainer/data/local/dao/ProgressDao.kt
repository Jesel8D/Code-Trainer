package com.CodeTrainer.codetrainer.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.CodeTrainer.codetrainer.data.local.entity.ProgressEntity

@Dao
interface ProgressDao {

    // Cuando el usuario guarde, usamos esto. Si ya existe, lo reemplaza.
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun upsertProgress(progress: ProgressEntity) // "upsert" = update or insert

    @Query("SELECT * FROM progress WHERE exerciseId = :exerciseId")
    suspend fun getProgressForExercise(exerciseId: Int): ProgressEntity?
}