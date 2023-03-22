plugins {
    id("enemy.android.library")
    id("enemy.android.hilt")
}

android {
    namespace = "com.buggily.enemy.core.local"
}

dependencies {
    implementation(project(":local:track"))
    implementation(project(":local:playlist"))

    implementation(libs.kotlinx.datetime)

    implementation(libs.androidx.room)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    kapt(libs.androidx.room.compiler)
}
