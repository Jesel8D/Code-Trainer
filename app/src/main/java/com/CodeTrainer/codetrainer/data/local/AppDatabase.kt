package com.CodeTrainer.codetrainer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.CodeTrainer.codetrainer.data.local.dao.ExerciseDao
import com.CodeTrainer.codetrainer.data.local.dao.ProgressDao
import com.CodeTrainer.codetrainer.data.local.dao.StatsDao
import com.CodeTrainer.codetrainer.data.local.dao.TipDao
import com.CodeTrainer.codetrainer.data.local.entity.ExerciseEntity
import com.CodeTrainer.codetrainer.data.local.entity.ProgressEntity
import com.CodeTrainer.codetrainer.data.local.entity.StatsEntity
import com.CodeTrainer.codetrainer.data.local.entity.TipEntity

// 1. Anotamos la clase como una Base de Datos de Room
@Database(
    // 2. Listamos todas las @Entity que debe manejar
    entities = [
        ExerciseEntity::class,
        ProgressEntity::class,
        StatsEntity::class,
        TipEntity::class
    ],
    // 3. Incrementa este número cada vez que cambies el esquema (migración)
    version = 1,
    // 4. No necesitamos esto para un proyecto de clase, evita un warning
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // 5. Room implementará estas funciones abstractas por nosotros
    abstract fun exerciseDao(): ExerciseDao
    abstract fun progressDao(): ProgressDao
    abstract fun statsDao(): StatsDao
    abstract fun tipDao(): TipDao

}