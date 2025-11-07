package com.CodeTrainer.codetrainer.domain.model

// Un modelo simple que representa al usuario logueado
data class AuthUser(
    val uid: String, val email: String?
)