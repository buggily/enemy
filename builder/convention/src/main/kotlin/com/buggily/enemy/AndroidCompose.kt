package com.buggily.enemy

import com.android.build.api.dsl.CommonExtension
import com.buggily.enemy.ext.getLibs
import com.buggily.enemy.ext.getVersion
import org.gradle.api.Project

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *>,
) = with(commonExtension) {

    compileSdk = 33

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = getLibs().getVersion("androidxComposeCompiler").toString()
    }
}
