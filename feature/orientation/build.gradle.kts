plugins {
    id("enemy.android.library.compose")
    id("enemy.android.library.feature")
}

android {
    namespace = "com.buggily.enemy.feature.orientation"
}

dependencies {
    implementation(project(":domain:navigation"))
}
