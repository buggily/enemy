plugins {
    id("enemy.android.library.data")
}

android {
    namespace = "com.buggily.enemy.data.playlistWithTracks"
}

dependencies {
    implementation(project(":data:track"))
    implementation(project(":data:playlist"))
}
