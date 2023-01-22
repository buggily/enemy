plugins {
    id("enemy.android.library")
    id("enemy.android.library.feature")
    id("enemy.android.library.compose")
}

android {
    namespace = "com.buggily.enemy.controller"
}

dependencies {
    implementation(libs.androidx.media.core)
    implementation(libs.androidx.media.ui)
}
