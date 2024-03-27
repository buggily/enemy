plugins {
    id("enemy.android.library")
    id("enemy.android.library.compose")
    id("enemy.android.hilt")

    id("kotlin-parcelize")
}

android {
    namespace = "com.buggily.enemy.core.ui"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
    implementation(project(":core:ext"))

    implementation(project(":core:navigation"))
    implementation(project(":domain:navigation"))

    implementation(project(":data:album"))
    implementation(project(":domain:album"))

    implementation(project(":data:track"))
    implementation(project(":domain:track"))

    implementation(project(":data:playlist"))
    implementation(project(":domain:playlist"))

    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)

    implementation(libs.androidx.media.core)
    implementation(libs.androidx.media.ui)

    implementation(libs.coil)
    implementation(libs.coil.compose)
}
