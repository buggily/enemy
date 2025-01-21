plugins {
    id("enemy.android.library")
    id("enemy.android.library.compose")
    id("enemy.android.hilt")

    id("kotlin-parcelize")
}

android {
    namespace = "com.buggily.enemy.core.ui"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:domain"))

    implementation(project(":core:ext"))
    implementation(project(":core:navigation"))

    implementation(project(":domain:theme"))
    implementation(project(":domain:album"))
    implementation(project(":domain:track"))
    implementation(project(":domain:playlist"))
    implementation(project(":domain:playlistWithTracks"))

    implementation(project(":domain:navigation"))

    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)

    implementation(libs.androidx.media.core)
    implementation(libs.androidx.media.ui)

    implementation(libs.coil)
    implementation(libs.coil.compose)
}
