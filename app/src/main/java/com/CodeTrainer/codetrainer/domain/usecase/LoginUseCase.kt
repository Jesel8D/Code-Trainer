package com.CodeTrainer.codetrainer.domain.usecase

import com.CodeTrainer.codetrainer.domain.model.AuthUser
import com.CodeTrainer.codetrainer.domain.repository.AuthRepository
import com.CodeTrainer.codetrainer.domain.repository.AuthResult
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    // Esta es una función "suspend" porque llama a Firebase (una API)
    suspend operator fun invoke(email: String, password: String): AuthResult<AuthUser> {
        return repository.login(email, password)
    }
}