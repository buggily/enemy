plugins {
    id("enemy.android.library")
    id("enemy.android.library.compose")
}

android {
    namespace = "com.buggily.enemy.core.ui"
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)

    implementation(libs.androidx.media.core)
    implementation(libs.androidx.media.ui)

    implementation(libs.coil)
    implementation(libs.coil.compose)
}
