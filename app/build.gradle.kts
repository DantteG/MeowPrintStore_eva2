plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.meowprint.store"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.meowprint.store"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" // Runner para tests instrumentados
        // Configuración de endpoints Xano (reemplaza los valores por los tuyos)
        buildConfigField("String", "XANO_STORE_BASE", "\"https://x8ki-letl-twmt.n7.xano.io/api:Bcv1qHHX\"") // Base URL Store (Xano)
        buildConfigField("String", "XANO_AUTH_BASE", "\"https://x8ki-letl-twmt.n7.xano.io/api:eg9IqgHd\"") // Base URL Auth (Xano)
        buildConfigField("int", "XANO_TOKEN_TTL_SEC", "86400") // TTL de token simulado
    }

    buildTypes { // Tipos de build (debug/release)
        release { // Configuración para versión release
            isMinifyEnabled = false // No minificar para facilitar depuración
            proguardFiles( // Archivos de reglas Proguard/R8
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }
}



dependencies {
    // Kotlin Standard Library
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.2.20")

    // Android UI
    implementation("androidx.core:core-ktx:1.17.0")
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.13.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("androidx.recyclerview:recyclerview:1.4.0")
    implementation("androidx.activity:activity-ktx:1.11.0")
    implementation("androidx.fragment:fragment-ktx:1.8.9")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")

    // Networking
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")
    implementation("com.squareup.okhttp3:okhttp:5.2.1")
    implementation("com.squareup.okhttp3:logging-interceptor:5.2.1")

    // Add Glide for image loading
    implementation("com.github.bumptech.glide:glide:5.0.5")
}
