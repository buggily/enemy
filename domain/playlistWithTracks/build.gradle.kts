plugins {
    id("enemy.android.library.domain")
}

android {
    namespace = "com.buggily.enemy.domain.playlistWithTracks"
}

dependencies {
    implementation(project(":data:track"))
    implementation(project(":data:playlist"))
    implementation(project(":data:playlistWithTracks"))
}
