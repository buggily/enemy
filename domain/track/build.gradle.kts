plugins {
    id("enemy.android.library.domain")
}

android {
    namespace = "com.buggily.enemy.domain.track"
}

dependencies {
    implementation(project(":data:track"))

    implementation(libs.androidx.media.core)
    implementation(libs.androidx.paging.core)
}
