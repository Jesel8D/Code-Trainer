package com.CodeTrainer.codetrainer.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.CodeTrainer.codetrainer.data.local.ExerciseWithProgress
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    // Usamos @Transaction para asegurar que la consulta de Ejercicio y Progreso
    // se haga de forma atómica (o todo o nada).
    @Transaction
    @Query("SELECT * FROM exercises WHERE id = :id")
    fun getExerciseWithProgressById(id: Int): Flow<ExerciseWithProgress>

    @Transaction
    @Query("SELECT * FROM exercises")
    fun getAllExercisesWithProgress(): Flow<List<ExerciseWithProgress>>

    @Transaction
    @Query("SELECT * FROM exercises WHERE level = :level")
    fun getExercisesWithProgressByLevel(level: String): Flow<List<ExerciseWithProgress>>

    // También podrías necesitar insertar ejercicios (tal vez en una precarga)
    // @Insert(onConflict = OnConflictStrategy.REPLACE)
    // suspend fun insertExercises(exercises: List<com.CodeTrainer.codetrainer.data.local.entity.ExerciseEntity>)
}