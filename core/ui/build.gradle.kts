plugins {
    id("enemy.android.library")
    id("enemy.android.library.compose")
    id("enemy.android.hilt")
}

android {
    namespace = "com.buggily.enemy.core.ui"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":core:ext"))

    implementation(project(":domain:navigation"))

    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)

    implementation(libs.androidx.media.core)
    implementation(libs.androidx.media.ui)

    implementation(libs.coil)
    implementation(libs.coil.compose)
}
