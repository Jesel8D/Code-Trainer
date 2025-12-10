package com.CodeTrainer.codetrainer.domain.repository

import android.net.Uri

interface ProfileImageRepository {
    suspend fun uploadProfileImage(userId: String, imageUri: Uri): Result<String>
    suspend fun getProfileImageUrl(userId: String): Result<String?>
    suspend fun deleteProfileImage(userId: String): Result<Unit>
}