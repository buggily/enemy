plugins {
    id("enemy.android.library")
    id("enemy.android.library.domain")
}

android {
    namespace = "com.buggily.enemy.domain.album"
}

dependencies {
    implementation(project(":data:album"))
}
