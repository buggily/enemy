plugins {
    id("enemy.android.library")
    id("enemy.android.library.local")
}

android {
    namespace = "com.buggily.enemy.local.playlist"
}

dependencies {
    implementation(libs.kotlinx.datetime)

    implementation(libs.androidx.room)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    kapt(libs.androidx.room.compiler)
}
