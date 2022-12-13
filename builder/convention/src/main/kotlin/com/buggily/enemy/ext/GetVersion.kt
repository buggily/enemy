package com.buggily.enemy.ext

import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionConstraint

fun VersionCatalog.getVersion(
    alias: String,
): VersionConstraint = findVersion(alias).get()
