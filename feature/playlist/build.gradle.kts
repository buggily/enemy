plugins {
    id("enemy.android.library.compose")
    id("enemy.android.library.feature")
    id("kotlin-parcelize")
}

android {
    namespace = "com.buggily.enemy.feature.playlist"
}

dependencies {
    implementation(project(":domain:playlist"))
    implementation(project(":data:playlist"))

    implementation(project(":domain:track"))
    implementation(project(":data:track"))

    implementation(project(":domain:controller"))
    implementation(project(":domain:navigation"))

    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.media.core)

    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)
}
