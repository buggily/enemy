plugins {
    id("enemy.android.library.compose")
    id("enemy.android.library.feature")
}

android {
    namespace = "com.buggily.enemy.feature.browse"
}

dependencies {
    implementation(project(":feature:albums"))
    implementation(project(":data:album"))

    implementation(project(":feature:tracks"))
    implementation(project(":data:track"))

    implementation(project(":feature:playlists"))
    implementation(project(":data:playlist"))
}

