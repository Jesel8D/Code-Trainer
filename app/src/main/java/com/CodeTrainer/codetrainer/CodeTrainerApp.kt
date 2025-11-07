package com.CodeTrainer.codetrainer

import dagger.hilt.android.HiltAndroidApp
import android.app.Application

//Anotamos la clase con la @HiltAndroidApp
@HiltAndroidApp
class CodeTrainerApp : Application() {
    //Por ahora estara vacio
    //Hilt se encarga de generar el codigo necesario
}