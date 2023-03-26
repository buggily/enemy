plugins {
    id("enemy.android.library.domain")
}

android {
    namespace = "com.buggily.enemy.domain.album"
}

dependencies {
    implementation(project(":data:album"))
    implementation(libs.androidx.paging.core)
}
