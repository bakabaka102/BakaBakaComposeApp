import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    //id("kotlin-kapt")
    alias(libs.plugins.kotlin.kapt)
    //id("dagger.hilt.android.plugin")
    alias(libs.plugins.hilt)
    //id("org.jetbrains.kotlin.plugin.serialization") version "2.2.0"
    alias(libs.plugins.kotlin.serialization) /*version "2.2.0"*/ //Missing can not convert json from remote server
}

android {
    compileSdk = 36
    namespace = "hn.single.network"
    defaultConfig {
        minSdk = 24

        val apiKey = getApiKey()
        buildConfigField("String", "API_KEY", apiKey)
    }
    /*namespace = "hn.single.network"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }*/
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

}

/*kotlin {
    jvmToolchain { 17 }
}*/

dependencies {

    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.hilt.android)
    implementation(libs.hilt.compiler)
    kapt(libs.hilt.android.compiler)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.core.ktx)
    //implementation(libs.androidx.appcompat)
    //implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

private fun getApiKey(): String {
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
