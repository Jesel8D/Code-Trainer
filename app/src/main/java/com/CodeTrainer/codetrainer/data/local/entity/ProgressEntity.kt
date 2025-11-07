package com.CodeTrainer.codetrainer.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "progress",
    foreignKeys = [
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.Companion.CASCADE // Si se borra un ejercicio, se borra el progreso
        )
    ],
    indices = [Index(value = ["exerciseId"])] // Para búsquedas más rápidas
)
data class ProgressEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val exerciseId: Int, // Clave foránea
    val status: String, // (ej. "PENDING", "COMPLETED")
    val completedAt: Long?, // Timestamp de cuándo se completó
    val score: Double, // Tu esquema lo tenía
    val userSolution: String // Guardamos lo que el usuario escribió
)