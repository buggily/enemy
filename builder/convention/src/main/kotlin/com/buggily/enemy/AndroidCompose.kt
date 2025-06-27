package com.buggily.enemy

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) = with(commonExtension) {

    compileSdk = 36

    buildFeatures {
        compose = true
    }
}
