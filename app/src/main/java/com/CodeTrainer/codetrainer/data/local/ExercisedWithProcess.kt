package com.CodeTrainer.codetrainer.data.local

import androidx.room.Embedded
import androidx.room.Relation
import com.CodeTrainer.codetrainer.data.local.entity.ExerciseEntity
import com.CodeTrainer.codetrainer.data.local.entity.ProgressEntity

// Esta clase POJO (Plain Old Kotlin Object) combina un Ejercicio
// con su Progreso (que puede ser nulo si no se ha iniciado).
data class ExerciseWithProgress(
    @Embedded
    val exercise: ExerciseEntity,

    @Relation(
        parentColumn = "id", // ID de com.CodeTrainer.codetrainer.data.local.entity.ExerciseEntity
        entityColumn = "exerciseId" // ID de com.CodeTrainer.codetrainer.data.local.entity.ProgressEntity
    )
    val progress: ProgressEntity?
)