plugins {
    id("enemy.android.library.compose")
    id("enemy.android.library.feature")
}

android {
    namespace = "com.buggily.enemy.feature.tracks"
}

dependencies {
    implementation(project(":domain:track"))
    implementation(project(":data:track"))

    implementation(project(":domain:controller"))

    implementation(libs.androidx.media.core)

    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)
}
