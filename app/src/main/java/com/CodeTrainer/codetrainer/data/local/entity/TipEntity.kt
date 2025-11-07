package com.CodeTrainer.codetrainer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tips")
data class TipEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val category: String, // (ej. "Python", "Buenas Prácticas")
    val content: String // El texto del consejo
)