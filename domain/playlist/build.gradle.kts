plugins {
    id("enemy.android.library.domain")
}

android {
    namespace = "com.buggily.enemy.domain.playlist"
}

dependencies {
    implementation(project(":data:playlist"))
    implementation(libs.androidx.paging.core)
}
