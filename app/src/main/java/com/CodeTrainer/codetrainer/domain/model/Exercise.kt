package com.CodeTrainer.codetrainer.domain.model

// Un modelo de datos simple (POKO)
// Nota que no tiene @Entity ni nada de Room. Es puro Kotlin.
data class Exercise(
    val id: Int,
    val title: String,
    val description: String,
    val language: String,
    val level: String,
    val solutionCode: String,
    val hint: String
)