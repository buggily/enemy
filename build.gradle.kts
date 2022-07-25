buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Dependency.Plugin.Gradle.ANDROID)
        classpath(Dependency.Plugin.Gradle.HILT)

        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
        classpath("com.android.tools.build:gradle:7.2.1")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
