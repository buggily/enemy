plugins {
    id("enemy.android.library.compose")
    id("enemy.android.library.feature")
}

android {
    namespace = "com.buggily.enemy.feature.preferences"
}

dependencies {
    implementation(project(":domain:theme"))
    implementation(project(":data:theme"))
}
