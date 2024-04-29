plugins {
    id("enemy.android.library.compose")
    id("enemy.android.library.feature")
}

android {
    namespace = "com.buggily.enemy.feature.browse"
}

dependencies {
    implementation(project(":feature:recent"))

    implementation(project(":feature:albums"))
    implementation(project(":domain:album"))

    implementation(project(":feature:tracks"))
    implementation(project(":domain:track"))

    implementation(project(":feature:playlists"))
    implementation(project(":domain:playlist"))
}

