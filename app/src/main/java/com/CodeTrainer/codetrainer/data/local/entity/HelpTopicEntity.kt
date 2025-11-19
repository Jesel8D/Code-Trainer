package com.CodeTrainer.codetrainer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "help_topics")
data class HelpTopicEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val category: String, // "getting_started", "tutorials", "faq", "glossary"
    val topicOrder: Int = 0 // CAMBIADO: de 'order' a 'topicOrder'
)