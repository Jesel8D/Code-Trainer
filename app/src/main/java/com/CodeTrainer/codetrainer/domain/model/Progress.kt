package com.CodeTrainer.codetrainer.domain.model

data class Progress(
    val exerciseId: Int,
    val status: ProgressStatus,
    val completedAt: Long?,
    val score: Double,
    val userSolution: String
)

// Usar un "enum class" es más limpio que usar Strings ("PENDING")
enum class ProgressStatus {
    PENDING,
    COMPLETED
}