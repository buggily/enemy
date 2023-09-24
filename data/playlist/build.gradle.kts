plugins {
    id("enemy.android.library.data")
}

android {
    namespace = "com.buggily.enemy.data.playlist"
}

dependencies {
    implementation(project(":local:playlist"))

    implementation(libs.kotlinx.dateTime)
    implementation(libs.androidx.paging.core)
}
