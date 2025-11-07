package com.CodeTrainer.codetrainer.domain.usecase

import com.CodeTrainer.codetrainer.domain.model.AuthUser
import com.CodeTrainer.codetrainer.domain.repository.AuthRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    // Lo llamamos como una función, Hilt inyecta el repositorio
    operator fun invoke(): AuthUser? {
        return repository.getCurrentUser()
    }
}