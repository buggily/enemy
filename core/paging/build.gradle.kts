plugins {
    id("enemy.android.library")
    id("enemy.android.hilt")
}

android {
    namespace = "com.buggily.enemy.core.paging"
}

dependencies {
    implementation(project(":core:query"))
    implementation(libs.androidx.paging.core)
}
