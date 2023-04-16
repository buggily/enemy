plugins {
    id("enemy.android.library.data")
}

android {
    namespace = "com.buggily.enemy.data.album"
}

dependencies {
    implementation(project(":external:album"))
    implementation(libs.androidx.paging.core)
}
