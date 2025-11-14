package com.CodeTrainer.codetrainer.data.repository

import com.CodeTrainer.codetrainer.domain.model.AuthUser
import com.CodeTrainer.codetrainer.domain.repository.AuthRepository
import com.CodeTrainer.codetrainer.domain.repository.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest // ¡Importante!
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override fun getCurrentUser(): AuthUser? {
        // Esto ahora devolverá el nombre si existe
        return auth.currentUser?.toAuthUser()
    }

    override suspend fun login(email: String, password: String): AuthResult<AuthUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            // Usamos !! porque si el await() no lanza excepción, user no es nulo
            val user = result.user!!.toAuthUser()
            AuthResult.Success(user)
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }

    // --- ¡ESTA ES LA FUNCIÓN CORREGIDA! ---
    override suspend fun register(email: String, password: String, name: String): AuthResult<AuthUser> { // 1. Añadido 'name'
        return try {
            // 2. Crea el usuario
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user!! // ¡No es nulo si tuvo éxito!

            // 3. Crea la solicitud para actualizar el perfil
            val profileUpdates = userProfileChangeRequest {
                displayName = name
            }
            // 4. Aplica el cambio y espera a que termine
            user.updateProfile(profileUpdates).await()

            // 5. Devuelve nuestro modelo AuthUser, que ahora SÍ tiene el nombre
            AuthResult.Success(user.toAuthUser())
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }

    override suspend fun logout() {
        auth.signOut()
    }
}

// --- ¡ESTE ES EL MAPPER CORREGIDO! ---
private fun FirebaseUser.toAuthUser(): AuthUser {
    return AuthUser(
        uid = this.uid,
        email = this.email,
        displayName = this.displayName // 6. Añadido 'displayName'
    )
}