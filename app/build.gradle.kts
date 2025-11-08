plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.ksp)
    // 1. Plugin de Hilt añadido
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.google.services) // <-- ¡ASEGÚRATE DE QUE ESTÉ AQUÍ!
}

android {
    namespace = "com.CodeTrainer.codetrainer"
    compileSdk = 34 // 2. SDK estable

    defaultConfig {
        applicationId = "com.CodeTrainer.codetrainer"
        minSdk = 29
        targetSdk = 34 // 3. SDK estable
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        // 4. Java 17 es el estándar moderno
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        // 5. Versión del compilador compatible con Kotlin 2.0.0
        kotlinCompilerExtensionVersion = "1.5.13"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // ...
    // --- FIREBASE ---
    implementation(platform(libs.firebase.bom)) // <-- AÑADE ESTA
    implementation(libs.firebase.auth.ktx)      // <-- AÑADE ESTA
    implementation(libs.firebase.analytics.ktx) // <-- AÑADE ESTA
    implementation(libs.firebase.appcheck.playintegrity)
// Add the dependencies for the App Check libraries
    // --- Pruebas (Testing) ---
    // ...

    // 6. Archivo de dependencias limpio y completo

    // --- Core y UI de Jetpack Compose ---
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // --- Core de AndroidX ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)

    // --- Navegación ---
    implementation(libs.androidx.navigation.compose)

    // --- ViewModel y Ciclo de Vida ---
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // --- Room (Base de Datos Local) ---
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler) // Procesador de anotaciones para Room

    // --- Hilt (Inyección de Dependencias) ---
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler) // Procesador de anotaciones para Hilt
    implementation(libs.androidx.hilt.navigation.compose)

    // --- Coroutines (Asincronía) ---
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // --- Otros (Coil, Datastore) ---
    implementation(libs.coil.compose)
    implementation(libs.androidx.datastore.preferences)

    // --- Pruebas (Testing) ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}