plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.dagger)
    id("kotlin-parcelize")
    kotlin("kapt")
}

android {
    namespace = "com.example.gotogether"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.gotogether"
        minSdk = 26
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")


    implementation("androidx.compose.material3:material3:1.4.0-alpha07")
    implementation("androidx.compose.material:material-icons-extended")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    
    implementation("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.9.0")

    // dagger
    implementation(libs.dagger.hilt)
    implementation(libs.hilt.compose.navigation)
    kapt(libs.dagger.kapt)

    // navigation
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization)

    // coil
    implementation(libs.coil.compose)

    // google maps
    implementation(libs.maps.compose)
    implementation(libs.play.services.maps)
    implementation(libs.android.maps.utils)
    implementation(libs.volley)

    // retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}