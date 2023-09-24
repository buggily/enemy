plugins {
    id("enemy.android.library")
}

android {
    namespace = "com.buggily.enemy.core.data"
}

dependencies {
    implementation(libs.kotlinx.dateTime)
}

