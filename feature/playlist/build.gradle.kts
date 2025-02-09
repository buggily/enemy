plugins {
    id("enemy.android.library.compose")
    id("enemy.android.library.feature")
    id("kotlin-parcelize")
}

android {
    namespace = "com.buggily.enemy.feature.playlist"
}

dependencies {
    implementation(project(":domain:track"))
    implementation(project(":domain:playlist"))
    implementation(project(":domain:playlistWithTracks"))
    implementation(project(":domain:resume"))

    implementation(project(":domain:controller"))
    implementation(project(":domain:navigation"))

    implementation(libs.kotlinx.dateTime)
    implementation(libs.androidx.media.core)

    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)
}
