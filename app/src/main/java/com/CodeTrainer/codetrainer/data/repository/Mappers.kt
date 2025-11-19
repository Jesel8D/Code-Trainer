package com.CodeTrainer.codetrainer.data.repository

import com.CodeTrainer.codetrainer.data.local.ExerciseWithProgress
import com.CodeTrainer.codetrainer.data.local.entity.ExerciseEntity
import com.CodeTrainer.codetrainer.data.local.entity.ProgressEntity
import com.CodeTrainer.codetrainer.data.local.entity.StatsEntity
import com.CodeTrainer.codetrainer.data.local.entity.TipEntity
import com.CodeTrainer.codetrainer.data.local.entity.HelpTopicEntity
import com.CodeTrainer.codetrainer.domain.model.*

fun ExerciseEntity.toDomain(): Exercise =
    Exercise(
        id = id,
        title = title,
        description = description,
        language = language,
        level = level,
        solutionCode = solutionCode,
        hint = hint
    )

fun ProgressEntity.toDomain(): Progress =
    Progress(
        exerciseId = exerciseId,
        status = if (status == "COMPLETED") ProgressStatus.COMPLETED else ProgressStatus.PENDING,
        completedAt = completedAt,
        score = score,
        userSolution = userSolution
    )

fun ExerciseWithProgress.toDomain(): ExerciseDetails =
    ExerciseDetails(
        exercise = exercise.toDomain(),
        progress = progress?.toDomain()
    )

fun Progress.toEntity(): ProgressEntity =
    ProgressEntity(
        exerciseId = exerciseId,
        status = status.name,
        completedAt = completedAt,
        score = score,
        userSolution = userSolution,
        id = 0
    )

fun StatsEntity.toDomain(): Stats =
    Stats(
        totalExercisesCompleted = totalExercisesCompleted,
        totalTimeSpent = totalTimeSpent,
        averageScore = averageScore,
        dailyStreak = dailyStreak
    )

fun Stats.toEntity(): StatsEntity =
    StatsEntity(
        id = 1,
        totalExercisesCompleted = totalExercisesCompleted,
        totalTimeSpent = totalTimeSpent,
        averageScore = averageScore,
        dailyStreak = dailyStreak
    )

fun TipEntity.toDomain(): Tip =
    Tip(
        id = id,
        category = category,
        content = content
    )

// ACTUALIZADO: Mapper para HelpTopic
fun HelpTopicEntity.toDomain(): HelpTopic =
    HelpTopic(
        id = id,
        title = title,
        content = content,
        category = category,
        topicOrder = topicOrder // CAMBIADO
    )