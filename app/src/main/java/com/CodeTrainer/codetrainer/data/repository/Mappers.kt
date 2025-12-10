package com.CodeTrainer.codetrainer.data.repository

// Este archivo solo contendrá funciones de extensión para "traducir"
// modelos de la capa DATA a modelos de la capa DOMAIN.

import com.CodeTrainer.codetrainer.data.local.ExerciseWithProgress
import com.CodeTrainer.codetrainer.data.local.entity.ExerciseEntity
import com.CodeTrainer.codetrainer.data.local.entity.ProgressEntity
import com.CodeTrainer.codetrainer.data.local.entity.StatsEntity
import com.CodeTrainer.codetrainer.data.local.entity.TipEntity
import com.CodeTrainer.codetrainer.domain.model.Exercise
import com.CodeTrainer.codetrainer.domain.model.ExerciseDetails
import com.CodeTrainer.codetrainer.domain.model.Progress
import com.CodeTrainer.codetrainer.domain.model.ProgressStatus
import com.CodeTrainer.codetrainer.domain.model.Stats
import com.CodeTrainer.codetrainer.domain.model.Tip

// --- Mappers de Ejercicio y Progreso ---

fun ExerciseEntity.toDomain(): Exercise {
    return Exercise(
        id = this.id,
        title = this.title,
        description = this.description,
        language = this.language,
        level = this.level,
        solutionCode = this.solutionCode,
        hint = this.hint
    )
}

fun ProgressEntity.toDomain(): Progress {
    val statusEnum = when (this.status) {
        "NOT_STARTED" -> ProgressStatus.NOT_STARTED
        "IN_PROGRESS" -> ProgressStatus.IN_PROGRESS
        "COMPLETED" -> ProgressStatus.COMPLETED
        else -> ProgressStatus.NOT_STARTED
    }

    return Progress(
        exerciseId = this.exerciseId,
        status = statusEnum,
        completedAt = this.completedAt,
        score = this.score,
        userSolution = this.userSolution
    )
}

// Este es el traductor de la clase de relación
fun ExerciseWithProgress.toDomain(): ExerciseDetails {
    return ExerciseDetails(
        exercise = this.exercise.toDomain(),
        progress = this.progress?.toDomain() // El '?' es clave
    )
}

// Y el camino inverso para Guardar
fun Progress.toEntity(): ProgressEntity {
    return ProgressEntity(
        exerciseId = this.exerciseId,
        status = this.status.name, // .name convierte (COMPLETED -> "COMPLETED")
        completedAt = this.completedAt,
        score = this.score,
        userSolution = this.userSolution,
        id = 0 // Room se encargará de esto si es nuevo
    )
}

// --- Mappers de Stats y Tips ---

fun StatsEntity.toDomain(): Stats {
    return Stats(
        totalExercisesCompleted = this.totalExercisesCompleted,
        totalTimeSpent = this.totalTimeSpent,
        averageScore = this.averageScore,
        dailyStreak = this.dailyStreak
    )
}

fun Stats.toEntity(): StatsEntity {
    return StatsEntity(
        id = 1, // Siempre usamos el ID 1 para la única fila de stats
        totalExercisesCompleted = this.totalExercisesCompleted,
        totalTimeSpent = this.totalTimeSpent,
        averageScore = this.averageScore,
        dailyStreak = this.dailyStreak
    )
}

fun TipEntity.toDomain(): Tip {
    return Tip(
        category = this.category,
        content = this.content
    )
}
