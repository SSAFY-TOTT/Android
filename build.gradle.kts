// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath ("com.google.gms:google-services:4.3.15")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.1")
    }
}

plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
//    id ("org.jetbrains.kotlin.android") version "1.7.10" apply false
//    id ("org.jetbrains.kotlin.jvm") version "1.7.20" apply false
    id ("com.google.dagger.hilt.android") version "2.48" apply false
    id ("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false
}