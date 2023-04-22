package com.buggily.enemy

import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion

internal fun Project.configureKotlin(
    javaExtension: JavaPluginExtension,
) = with(javaExtension) {
    toolchain { languageVersion.set(JavaLanguageVersion.of(17)) }
}
