plugins {
    id("enemy.android.library")
    id("enemy.android.library.compose")
    id("enemy.android.hilt")
}

android {
    namespace = "com.buggily.enemy.core.navigation"
}

dependencies {
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
}

