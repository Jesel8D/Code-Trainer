package com.CodeTrainer.codetrainer.domain.model

data class UserPreferences(
    val isDarkMode: Boolean = false,
    val language: String = "es", // español por defecto
    val dailyRemindersEnabled: Boolean = false
)