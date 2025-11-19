package com.CodeTrainer.codetrainer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.CodeTrainer.codetrainer.data.local.dao.ExerciseDao
import com.CodeTrainer.codetrainer.data.local.dao.HelpTopicDao
import com.CodeTrainer.codetrainer.data.local.dao.ProgressDao
import com.CodeTrainer.codetrainer.data.local.dao.StatsDao
import com.CodeTrainer.codetrainer.data.local.dao.TipDao
import com.CodeTrainer.codetrainer.data.local.entity.ExerciseEntity
import com.CodeTrainer.codetrainer.data.local.entity.HelpTopicEntity
import com.CodeTrainer.codetrainer.data.local.entity.ProgressEntity
import com.CodeTrainer.codetrainer.data.local.entity.StatsEntity
import com.CodeTrainer.codetrainer.data.local.entity.TipEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@Database(
    entities = [
        ExerciseEntity::class,
        ProgressEntity::class,
        StatsEntity::class,
        TipEntity::class,
        HelpTopicEntity::class // NUEVO
    ],
    version = 2, // INCREMENTADO
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
    abstract fun progressDao(): ProgressDao
    abstract fun statsDao(): StatsDao
    abstract fun tipDao(): TipDao
    abstract fun helpTopicDao(): HelpTopicDao // NUEVO

    companion object {
        fun createCallback(): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // Usamos un scope para las operaciones de seed
                    CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
                        // El seed se hará mediante el DatabaseModule
                    }
                }
            }
        }
    }
}