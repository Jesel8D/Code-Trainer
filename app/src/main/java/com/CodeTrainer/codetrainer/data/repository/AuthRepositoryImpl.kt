package com.CodeTrainer.codetrainer.data.repository

import com.CodeTrainer.codetrainer.domain.model.AuthUser
import com.CodeTrainer.codetrainer.domain.repository.AuthRepository
import com.CodeTrainer.codetrainer.domain.repository.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth // Hilt nos dará esto
) : AuthRepository {

    override fun getCurrentUser(): AuthUser? {
        return auth.currentUser?.toAuthUser()
    }

    override suspend fun login(email: String, password: String): AuthResult<AuthUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user?.toAuthUser()!!
            AuthResult.Success(user)
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }

    override suspend fun register(email: String, password: String): AuthResult<AuthUser> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user?.toAuthUser()!!
            AuthResult.Success(user)
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }

    override suspend fun logout() {
        auth.signOut()
    }
}

// Mapper para convertir un FirebaseUser a nuestro AuthUser
private fun FirebaseUser.toAuthUser(): AuthUser {
    return AuthUser(
        uid = this.uid,
        email = this.email
    )
}