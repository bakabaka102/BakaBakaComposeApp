// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.kotlin.parcelize) apply false

    //#kotlin version = "2.0.0" need this line
    alias(libs.plugins.compose.compiler) apply false
    //id("com.google.dagger.hilt.android") version "2.56.2" apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    alias(libs.plugins.android.library) apply false //<-- this one //for Hilt
}