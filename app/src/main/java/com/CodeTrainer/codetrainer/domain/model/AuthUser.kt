package com.CodeTrainer.codetrainer.domain.model

data class AuthUser(
    val uid: String,
    val email: String?,
    val displayName: String? // Value para guardar el nombre que se mostrara en Home
)