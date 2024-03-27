plugins {
    id("enemy.android.library.compose")
    id("enemy.android.library.feature")
}

android {
    namespace = "com.buggily.enemy.feature.recent"
}

dependencies {
    implementation(project(":data:album"))
    implementation(project(":domain:album"))

    implementation(project(":data:track"))
    implementation(project(":domain:track"))

    implementation(project(":domain:navigation"))

    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)
}
