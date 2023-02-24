plugins {
    id("enemy.android.library")
    id("enemy.android.library.compose")
    id("enemy.android.library.feature")
}

android {
    namespace = "com.buggily.enemy.controller"
}

dependencies {
    implementation(libs.androidx.media.core)
    implementation(libs.androidx.media.ui)
}
