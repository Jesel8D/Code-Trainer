package com.CodeTrainer.codetrainer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val language: String,
    val level: String, // Esto es para la  descripción (básico, intermedio)
    val solutionCode: String, // El código base para el usuario
    val hint: String, // Consejo específico (para "Recibir ayuda rápida")
    val createdAt: Long = System.currentTimeMillis()
)