plugins {
    id("enemy.android.library")
    id("enemy.android.hilt")
}

android {
    namespace = "com.buggily.enemy.core.domain"
}

dependencies {
    implementation(project(":core:model"))
    implementation(libs.kotlinx.datetime)
}
