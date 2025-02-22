plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.forteur.airminireceiver"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.forteur.airminireceiver"
        minSdk = 33
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.leanback)
    implementation(libs.glide)

//    implementation fileTree(dir: 'libs', include: ['*.aar'])
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar"))))
    implementation(libs.androidx.runtime.android)
    implementation(libs.androidx.ui.android)

//    implementation 'androidx.core:core-ktx:1.12.0'
//    implementation 'androidx.activity:activity-compose:1.8.2' // Assicura che ComponentActivity funzioni con Compose
//    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.6.2' // Per usare lifecycleScope

    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.foundation.android)

    //material
    implementation("androidx.compose.material3:material3:1.0.0-alpha03")
//    implementation(libs.androidx.material3.android)

}