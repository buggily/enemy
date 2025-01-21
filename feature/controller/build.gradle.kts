plugins {
    id("enemy.android.library.compose")
    id("enemy.android.library.feature")
}

android {
    namespace = "com.buggily.enemy.controller"
}

dependencies {
    implementation(project(":domain:controller"))
    implementation(project(":domain:navigation"))
    implementation(project(":domain:track"))

    implementation(libs.androidx.media.core)
    implementation(libs.androidx.media.ui)
    implementation(libs.androidx.media.session)

    implementation(libs.kotlinx.dateTime)
    implementation(libs.androidx.compose.material3.windowSizeClass)
}
