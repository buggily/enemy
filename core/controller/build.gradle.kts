plugins {
    id("enemy.android.library")
    id("enemy.android.hilt")
}

android {
    namespace = "com.buggily.enemy.core.controller"
}

dependencies {
    implementation(project(":core:ext"))
    implementation(libs.androidx.media.core)
}
