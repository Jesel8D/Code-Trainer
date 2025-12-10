package com.CodeTrainer.codetrainer.data.repository

import android.net.Uri
import com.CodeTrainer.codetrainer.domain.repository.ProfileImageRepository
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileImageRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage
) : ProfileImageRepository {

    private val storageRef = storage.reference
    private val profileImagesRef = storageRef.child("profile_images")

    override suspend fun uploadProfileImage(userId: String, imageUri: Uri): Result<String> {
        return try {
            val imageRef = profileImagesRef.child("$userId.jpg")

            // Subir imagen
            imageRef.putFile(imageUri).await()

            // Obtener URL de descarga
            val downloadUrl = imageRef.downloadUrl.await().toString()

            Result.success(downloadUrl)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProfileImageUrl(userId: String): Result<String?> {
        return try {
            val imageRef = profileImagesRef.child("$userId.jpg")
            val url = imageRef.downloadUrl.await().toString()
            Result.success(url)
        } catch (e: Exception) {
            // Si no existe la imagen, retornamos null
            Result.success(null)
        }
    }

    override suspend fun deleteProfileImage(userId: String): Result<Unit> {
        return try {
            val imageRef = profileImagesRef.child("$userId.jpg")
            imageRef.delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}