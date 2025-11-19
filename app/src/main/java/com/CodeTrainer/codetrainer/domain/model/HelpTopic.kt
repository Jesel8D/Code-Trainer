package com.CodeTrainer.codetrainer.domain.model

data class HelpTopic(
    val id: Int,
    val title: String,
    val content: String,
    val category: String,
    val topicOrder: Int // CAMBIADO: de 'order' a 'topicOrder'
)
