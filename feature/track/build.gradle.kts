plugins {
    id("enemy.android.library.compose")
    id("enemy.android.library.feature")
}

android {
    namespace = "com.buggily.enemy.feature.track"
}

dependencies {
    implementation(project(":domain:track"))
    implementation(project(":domain:playlist"))
    implementation(project(":domain:playlistWithTracks"))

    implementation(project(":domain:navigation"))

    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)
}
