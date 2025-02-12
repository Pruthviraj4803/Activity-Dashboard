import java.util.EventListener

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
}
buildscript {
    repositories {
        google() // Add Google Maven repository
        jcenter()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:4.2.0")
    }
}
