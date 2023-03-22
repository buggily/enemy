plugins {
    id("enemy.android.library")
    id("enemy.android.library.domain")
}

android {
    namespace = "com.buggily.enemy.domain.navigation"
}

dependencies {
    implementation(project(":core:navigation"))
    implementation(libs.androidx.navigation.compose)
}
