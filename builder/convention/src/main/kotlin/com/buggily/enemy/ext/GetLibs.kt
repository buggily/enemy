package com.buggily.enemy.ext

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

fun Project.getLibs(): VersionCatalog {
    val extension: VersionCatalogsExtension = extensions.getByType()
    return extension.named("libs")
}
