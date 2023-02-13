plugins {
    id("enemy.android.library")
    id("enemy.android.library.feature")
    id("enemy.android.library.compose")
}

android {
    namespace = "com.buggily.enemy.feature.tracks"
}

dependencies {
    implementation(project(":domain:track"))

    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)
}
