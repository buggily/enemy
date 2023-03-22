plugins {
    id("enemy.android.library")
    id("enemy.android.library.compose")
    id("enemy.android.library.feature")
}

android {
    namespace = "com.buggily.enemy.feature.albums"
}

dependencies {
    implementation(project(":domain:album"))
    implementation(project(":domain:track"))
    implementation(project(":domain:navigation"))

    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)
}
