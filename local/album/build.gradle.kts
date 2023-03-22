plugins {
    id("enemy.android.library")
    id("enemy.android.library.local")
}

android {
    namespace = "com.buggily.enemy.local.album"
}

dependencies {
    implementation(project(":core:query"))
    implementation(project(":core:paging"))

    implementation(libs.androidx.paging.core)
}
