plugins {
    id("enemy.kotlin.library.local")
}

dependencies {
    implementation(libs.kotlinx.dateTime)
    implementation(libs.androidx.room.core)
    implementation(libs.androidx.paging.core)
}
