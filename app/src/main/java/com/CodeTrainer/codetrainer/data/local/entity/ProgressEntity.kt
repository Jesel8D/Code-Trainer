package com.CodeTrainer.codetrainer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "progress")
data class ProgressEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val exerciseId: Long,
    val status: String,            // "NOT_STARTED", "IN_PROGRESS", "COMPLETED"
    val completedAt: Long?,
    val score: Int?,
    val userSolution: String?
)
