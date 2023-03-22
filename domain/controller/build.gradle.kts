plugins {
    id("enemy.android.library")
    id("enemy.android.library.domain")
}

android {
    namespace = "com.buggily.enemy.domain.controller"
}

dependencies {
    implementation(project(":core:controller"))
    implementation(libs.androidx.media.core)
}

