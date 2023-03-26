plugins {
    id("enemy.android.library.local")
}

android {
    namespace = "com.buggily.enemy.local.track"
}

dependencies {
    implementation(project(":core:query"))
    implementation(project(":core:paging"))

    implementation(libs.androidx.paging.core)

    implementation(libs.androidx.room)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    kapt(libs.androidx.room.compiler)
}
