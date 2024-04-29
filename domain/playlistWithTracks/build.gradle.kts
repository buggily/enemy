plugins {
    id("enemy.android.library.domain")
}

android {
    namespace = "com.buggily.enemy.domain.playlistWithTracks"
}

dependencies {
    implementation(project(":domain:track"))
    implementation(project(":data:track"))

    implementation(project(":domain:playlist"))
    implementation(project(":data:playlist"))

    implementation(project(":data:playlistWithTracks"))
}
