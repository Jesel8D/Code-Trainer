package com.CodeTrainer.codetrainer.domain.model

data class Progress(
    val exerciseId: Long,
    val status: ProgressStatus,
    val completedAt: Long?,
    val score: Int?,
    val userSolution: String?
)
