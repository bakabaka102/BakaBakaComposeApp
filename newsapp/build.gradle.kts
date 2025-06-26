import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.parcelize)
    //kotlin("plugin.parcelize")
    id("kotlin-kapt")
    //id("dagger.hilt.android.plugin") version "2.53.1" // <--- Chú ý thêm version ở đây! // <--- thêm dòng này!
    id("com.google.dagger.hilt.android") version "2.56.2" apply false //<-- this one //for Hilt
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.0"
    //id("kotlinx-serialization")
}

android {
    namespace = "hn.news.app"
    compileSdk = 36

    defaultConfig {
        applicationId = "hn.news.app"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        val apiKey = getApiKey()
        buildConfigField("String", "API_KEY", apiKey)

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    /*kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }*/
    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "META-INF/gradle/incremental.annotation.processors"
        }
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {

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

    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.material)
    implementation(libs.androidx.foundation)

    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.hilt.android)
    implementation(libs.hilt.compiler)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.hilt.android.compiler)
    implementation(libs.coil.compose)

}

fun getApiKey(): String {
    val properties = Properties()
    val keystoreFile = project.rootProject.file("gradle.properties")
    properties.load(keystoreFile.inputStream())
    //val apiKey = properties.getProperty("apiKey") ?: ""

    val propertiesFile = project.rootProject.file("gradle.properties")
    val apiKey = if (propertiesFile.exists()) {
        properties.load(propertiesFile.inputStream())
        properties.getProperty("apiKey") ?: ""
    } else {
        System.getenv("apiKey") ?: ""
    }
    return apiKey
}