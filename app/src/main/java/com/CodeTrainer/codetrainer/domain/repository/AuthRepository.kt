package com.CodeTrainer.codetrainer.domain.repository

import com.CodeTrainer.codetrainer.domain.model.AuthUser
import kotlinx.coroutines.flow.Flow

// Definimos un resultado genérico para las llamadas a Firebase
sealed class AuthResult<out T> {
    data class Success<out T>(val data: T) : AuthResult<T>()
    data class Error(val exception: Exception) : AuthResult<Nothing>()
}

interface AuthRepository {
    fun getCurrentUser(): AuthUser?
    suspend fun login(email: String, password: String): AuthResult<AuthUser>
    suspend fun register(email: String, password: String): AuthResult<AuthUser>
    suspend fun logout()
}