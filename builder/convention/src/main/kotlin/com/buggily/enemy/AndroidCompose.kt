package com.buggily.enemy

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project

internal fun Project.configureAndroidCompose(
    applicationExtension: ApplicationExtension,
) = with(applicationExtension) {

    compileSdk = 36

    buildFeatures {
        compose = true
    }
}

internal fun Project.configureAndroidCompose(
    libraryExtension: LibraryExtension,
) = with(libraryExtension) {

    compileSdk = 36

    buildFeatures {
        compose = true
    }
}
