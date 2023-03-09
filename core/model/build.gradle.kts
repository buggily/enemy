plugins {
    id("enemy.android.library")
}

android {
    namespace = "com.buggily.enemy.core.model"
}

dependencies {
    implementation(libs.kotlinx.datetime)
}
