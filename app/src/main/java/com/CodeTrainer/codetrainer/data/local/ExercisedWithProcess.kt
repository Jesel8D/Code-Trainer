package com.CodeTrainer.codetrainer.data.local

import androidx.room.Embedded
import androidx.room.Relation
import com.CodeTrainer.codetrainer.data.local.entity.ExerciseEntity
import com.CodeTrainer.codetrainer.data.local.entity.ProgressEntity

data class ExerciseWithProgress(
    @Embedded val exercise: ExerciseEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "exerciseId"
    )
    val progress: ProgressEntity?
)