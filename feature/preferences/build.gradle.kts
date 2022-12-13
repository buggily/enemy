plugins {
    id("enemy.android.library")
    id("enemy.android.library.feature")
    id("enemy.android.library.compose")
}

android {
    namespace = "com.buggily.enemy.feature.preferences"
}

dependencies {
    implementation(project(":domain:theme"))
}
