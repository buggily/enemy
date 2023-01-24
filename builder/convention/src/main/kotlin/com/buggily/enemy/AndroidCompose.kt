package com.buggily.enemy

import com.android.build.api.dsl.CommonExtension
import com.buggily.enemy.ext.getLib
import com.buggily.enemy.ext.getLibs
import com.buggily.enemy.ext.getVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *>,
) = with(commonExtension) {

    val libs: VersionCatalog = getLibs()
    val bom: Provider<MinimalExternalModuleDependency> = libs.getLib("androidx-compose-bom")

    compileSdk = 33

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.getVersion("androidxComposeCompiler").toString()
    }

    dependencies {
        add("implementation", platform(bom))
        add("androidTestImplementation", platform(bom))

        add("implementation", libs.getLib("androidx.compose.material3"))
        add("implementation", libs.getLib("androidx.compose.material3.windowSizeClass"))
    }
}
