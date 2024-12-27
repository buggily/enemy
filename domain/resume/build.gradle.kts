plugins {
    id("enemy.android.library.domain")
}

android {
    namespace = "com.buggily.enemy.domain.resume"
}

dependencies {
    implementation(project(":data:resume"))
    implementation(project(":domain:track"))

    implementation(libs.androidx.media.core)
}
