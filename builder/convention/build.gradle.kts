plugins {
    `kotlin-dsl`
}

group = "com.buggily.enemy.builder"

java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(17)) }
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "enemy.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "enemy.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidHilt") {
            id = "enemy.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidLibrary") {
            id = "enemy.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "enemy.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibraryData") {
            id = "enemy.android.library.data"
            implementationClass = "AndroidLibraryDataConventionPlugin"
        }
        register("androidLibraryDomain") {
            id = "enemy.android.library.domain"
            implementationClass = "AndroidLibraryDomainConventionPlugin"
        }
        register("androidLibraryExternal") {
            id = "enemy.android.library.external"
            implementationClass = "AndroidLibraryExternalConventionPlugin"
        }
        register("androidLibraryFeature") {
            id = "enemy.android.library.feature"
            implementationClass = "AndroidLibraryFeatureConventionPlugin"
        }
        register("androidLibraryLocal") {
            id = "enemy.android.library.local"
            implementationClass = "AndroidLibraryLocalConventionPlugin"
        }
        register("androidTest") {
            id = "enemy.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }
        register("kotlinHilt") {
            id = "enemy.kotlin.hilt"
            implementationClass = "KotlinHiltConventionPlugin"
        }
        register("kotlinLibrary") {
            id = "enemy.kotlin.library"
            implementationClass = "KotlinLibraryConventionPlugin"
        }
        register("kotlinTest") {
            id = "enemy.kotlin.test"
            implementationClass = "KotlinTestConventionPlugin"
        }
    }
}
