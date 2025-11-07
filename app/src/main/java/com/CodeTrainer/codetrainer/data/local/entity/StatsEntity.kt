package com.CodeTrainer.codetrainer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stats")
data class StatsEntity(
    // Usamos un ID fijo para que siempre sea la misma fila
    @PrimaryKey
    val id: Int = 1,
    val totalExercisesCompleted: Int,
    val totalTimeSpent: Long, // Guardar en segundos o milisegundos
    val averageScore: Double,
    val dailyStreak: Int
)