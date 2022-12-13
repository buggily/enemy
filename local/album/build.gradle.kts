plugins {
    id("enemy.android.library")
    id("enemy.android.library.local")
    id("enemy.android.hilt")
}

android {
    namespace = "com.buggily.enemy.local.album"
}

dependencies {
    implementation(libs.androidx.paging.core)
}
