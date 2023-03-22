plugins {
    id("enemy.android.library")
    id("enemy.android.library.compose")
    id("enemy.android.library.feature")
}

android {
    namespace = "com.buggily.enemy.feature.browse"
}

dependencies {
    implementation(project(":feature:albums"))
    implementation(project(":feature:tracks"))
    implementation(project(":feature:playlists"))
}

