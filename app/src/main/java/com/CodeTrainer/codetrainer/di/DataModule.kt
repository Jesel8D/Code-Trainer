package com.CodeTrainer.codetrainer.di

import android.content.Context
import androidx.room.Room
import com.CodeTrainer.codetrainer.data.local.AppDatabase
import com.CodeTrainer.codetrainer.data.local.dao.ExerciseDao
import com.CodeTrainer.codetrainer.data.local.dao.ProgressDao
import com.CodeTrainer.codetrainer.data.local.dao.StatsDao
import com.CodeTrainer.codetrainer.data.local.dao.TipDao
import com.CodeTrainer.codetrainer.data.repository.AuthRepositoryImpl
import com.CodeTrainer.codetrainer.data.repository.ExerciseRepositoryImpl
import com.CodeTrainer.codetrainer.data.repository.StatsRepositoryImpl
import com.CodeTrainer.codetrainer.data.repository.TipRepositoryImpl
import com.CodeTrainer.codetrainer.domain.repository.AuthRepository
import com.CodeTrainer.codetrainer.domain.repository.ExerciseRepository
import com.CodeTrainer.codetrainer.domain.repository.StatsRepository
import com.CodeTrainer.codetrainer.domain.repository.TipRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    // --- PROVEEDOR DE FIREBASE ---
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    // --- PROVEEDORES DE ROOM ---
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "codetrainer_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideExerciseDao(db: AppDatabase): ExerciseDao = db.exerciseDao()

    @Provides
    @Singleton
    fun provideProgressDao(db: AppDatabase): ProgressDao = db.progressDao()

    @Provides
    @Singleton
    fun provideStatsDao(db: AppDatabase): StatsDao = db.statsDao()

    @Provides
    @Singleton
    fun provideTipDao(db: AppDatabase): TipDao = db.tipDao()
}

// --- MÓDULO DE REPOSITORIOS ---
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    // ¡¡ESTA ES LA RECETA QUE LE FALTABA A HILT!!
    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    // (Estos ya los tenías)
    @Binds
    @Singleton
    abstract fun bindExerciseRepository(
        exerciseRepositoryImpl: ExerciseRepositoryImpl
    ): ExerciseRepository

    @Binds
    @Singleton
    abstract fun bindStatsRepository(
        statsRepositoryImpl: StatsRepositoryImpl
    ): StatsRepository

    @Binds
    @Singleton
    abstract fun bindTipRepository(
        tipRepositoryImpl: TipRepositoryImpl
    ): TipRepository
}