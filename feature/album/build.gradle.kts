plugins {
    id("enemy.android.library.compose")
    id("enemy.android.library.feature")
}

android {
    namespace = "com.buggily.enemy.feature.album"
}

dependencies {
    implementation(project(":domain:album"))
    implementation(project(":domain:track"))
    implementation(project(":domain:resume"))

    implementation(project(":domain:controller"))
    implementation(project(":domain:navigation"))

    implementation(libs.androidx.media.core)

    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.compose.material3.windowSizeClass)
}
