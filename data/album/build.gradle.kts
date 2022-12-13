plugins {
    id("enemy.android.library")
    id("enemy.android.library.data")
}

android {
    namespace = "com.buggily.enemy.data.album"
}

dependencies {
    implementation(project(":local:album"))
    implementation(project(":core:query"))
    implementation(project(":core:paging"))

    implementation(libs.androidx.paging.core)
}
