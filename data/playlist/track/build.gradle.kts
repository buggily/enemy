plugins {
    id("enemy.android.library.data")
}

android {
    namespace = "com.buggily.enemy.data.playlist.track"
}

dependencies {
    implementation(project(":local:playlist:track"))
    implementation(project(":external:track"))

    implementation(libs.kotlinx.dateTime)
    implementation(libs.androidx.paging.core)
}
