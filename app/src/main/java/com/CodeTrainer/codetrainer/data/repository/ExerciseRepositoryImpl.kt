package com.CodeTrainer.codetrainer.data.repository

import com.CodeTrainer.codetrainer.data.local.dao.ExerciseDao
import com.CodeTrainer.codetrainer.data.local.dao.ProgressDao
import com.CodeTrainer.codetrainer.domain.model.ExerciseDetails
import com.CodeTrainer.codetrainer.domain.model.Progress
import com.CodeTrainer.codetrainer.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// 1. Inyectamos los DAOs que necesitamos
class ExerciseRepositoryImpl @Inject constructor(
    private val exerciseDao: ExerciseDao, private val progressDao: ProgressDao
) : ExerciseRepository { // 2. Implementamos la interfaz del Dominio

    // 3. Sobrescribimos los métodos del "contrato"
    override fun getAllExercises(): Flow<List<ExerciseDetails>> {
        // Llamamos al DAO, y "mapeamos" el resultado a nuestro modelo del Dominio
        return exerciseDao.getAllExercisesWithProgress().map { list ->
            list.map { it.toDomain() } // Usamos el mapper
        }
    }

    override fun getExerciseDetails(id: Int): Flow<ExerciseDetails?> {
        return exerciseDao.getExerciseWithProgressById(id).map {
            it?.toDomain() // Usamos el mapper
        }
    }

    override suspend fun saveProgress(progress: Progress) {
        // Usamos el mapper inverso para convertir el modelo de Dominio a Entity
        progressDao.upsertProgress(progress.toEntity())
    }
}