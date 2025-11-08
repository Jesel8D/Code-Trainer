package com.CodeTrainer.codetrainer.domain.usecase

import com.CodeTrainer.codetrainer.domain.model.AuthUser
import com.CodeTrainer.codetrainer.domain.repository.AuthRepository
import com.CodeTrainer.codetrainer.domain.repository.AuthResult
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    // Función "suspend" porque llama a la API de Firebase
    suspend operator fun invoke(email: String, password: String): AuthResult<AuthUser> {
        return repository.register(email, password)

        // NOTA: Firebase Auth no guarda el "Nombre Completo" por defecto.
        // Si quisiéramos guardarlo, tendríamos que añadir otra llamada aquí
        // a un "ProfileRepository" que lo guarde en Firestore, pero
        // para un registro simple, esto es suficiente.
    }
}