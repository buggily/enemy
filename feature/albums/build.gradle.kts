plugins {
    id("enemy.android.library")
    id("enemy.android.library.feature")
    id("enemy.android.library.compose")
}

android {
    namespace = "com.buggily.enemy.feature.albums"
}

dependencies {
    implementation(project(":domain:album"))
    implementation(project(":domain:track"))

    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)
}
