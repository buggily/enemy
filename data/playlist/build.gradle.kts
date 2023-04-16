plugins {
    id("enemy.android.library.data")
}

android {
    namespace = "com.buggily.enemy.data.playlist"
}

dependencies {
    implementation(project(":local:playlist"))

    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.paging.core)
}
