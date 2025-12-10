package com.CodeTrainer.codetrainer.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.CodeTrainer.codetrainer.data.local.AppDatabase
import com.CodeTrainer.codetrainer.data.local.DatabaseSeeder
import com.CodeTrainer.codetrainer.data.local.dao.ExerciseDao
import com.CodeTrainer.codetrainer.data.local.dao.HelpTopicDao
import com.CodeTrainer.codetrainer.data.local.dao.ProgressDao
import com.CodeTrainer.codetrainer.data.local.dao.StatsDao
import com.CodeTrainer.codetrainer.data.local.dao.TipDao
import com.CodeTrainer.codetrainer.data.repository.AuthRepositoryImpl
import com.CodeTrainer.codetrainer.data.repository.ExerciseRepositoryImpl
import com.CodeTrainer.codetrainer.data.repository.HelpRepositoryImpl
import com.CodeTrainer.codetrainer.data.repository.PreferencesRepositoryImpl
import com.CodeTrainer.codetrainer.data.repository.ProfileImageRepositoryImpl
import com.CodeTrainer.codetrainer.data.repository.StatsRepositoryImpl
import com.CodeTrainer.codetrainer.data.repository.TipRepositoryImpl
import com.CodeTrainer.codetrainer.domain.repository.AuthRepository
import com.CodeTrainer.codetrainer.domain.repository.ExerciseRepository
import com.CodeTrainer.codetrainer.domain.repository.HelpRepository
import com.CodeTrainer.codetrainer.domain.repository.PreferencesRepository
import com.CodeTrainer.codetrainer.domain.repository.ProfileImageRepository
import com.CodeTrainer.codetrainer.domain.repository.StatsRepository
import com.CodeTrainer.codetrainer.domain.repository.TipRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "codetrainer_db"
        )
            .fallbackToDestructiveMigration()
            .addCallback(DatabaseCallback())
            .build()
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

    @Provides
    @Singleton
    fun provideHelpTopicDao(db: AppDatabase): HelpTopicDao = db.helpTopicDao()

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }
}

private class DatabaseCallback : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            seedDatabase(db)
        }
    }

    /**
     * Inserta datos iniciales usando DatabaseSeeder.
     * Usamos bucles for en lugar de lambdas para evitar problemas
     * de inferencia de tipos en el parámetro `exercise`.
     */
    private fun seedDatabase(db: SupportSQLiteDatabase) {
        // Python
        val pythonExercises = DatabaseSeeder.getPythonExercises()
        for (exercise in pythonExercises) {
            db.execSQL(
                "INSERT INTO exercises (title, description, language, level, solutionCode, hint, createdAt) VALUES (?, ?, ?, ?, ?, ?, ?)",
                arrayOf(
                    exercise.title,
                    exercise.description,
                    exercise.language,
                    exercise.level,
                    exercise.solutionCode,
                    exercise.hint,
                    System.currentTimeMillis()
                )
            )
        }

        // C++
        val cppExercises = DatabaseSeeder.getCppExercises()
        for (exercise in cppExercises) {
            db.execSQL(
                "INSERT INTO exercises (title, description, language, level, solutionCode, hint, createdAt) VALUES (?, ?, ?, ?, ?, ?, ?)",
                arrayOf(
                    exercise.title,
                    exercise.description,
                    exercise.language,
                    exercise.level,
                    exercise.solutionCode,
                    exercise.hint,
                    System.currentTimeMillis()
                )
            )
        }

        // Tips
        val tips = DatabaseSeeder.getTips()
        for (tip in tips) {
            db.execSQL(
                "INSERT INTO tips (category, content) VALUES (?, ?)",
                arrayOf(tip.category, tip.content)
            )
        }

        // Help topics
        val helpTopics = DatabaseSeeder.getHelpTopics()
        for (topic in helpTopics) {
            db.execSQL(
                "INSERT INTO help_topics (title, content, category, topicOrder) VALUES (?, ?, ?, ?)",
                arrayOf(topic.title, topic.content, topic.category, topic.topicOrder)
            )
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

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

    @Binds
    @Singleton
    abstract fun bindPreferencesRepository(
        preferencesRepositoryImpl: PreferencesRepositoryImpl
    ): PreferencesRepository

    @Binds
    @Singleton
    abstract fun bindHelpRepository(
        helpRepositoryImpl: HelpRepositoryImpl
    ): HelpRepository

    @Binds
    @Singleton
    abstract fun bindProfileImageRepository(
        profileImageRepositoryImpl: ProfileImageRepositoryImpl
    ): ProfileImageRepository
}
