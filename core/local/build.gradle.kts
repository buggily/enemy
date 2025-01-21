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
    implementation(project(":local:playlist:track"))
    implementation(project(":local:preferences"))

    implementation(libs.kotlinx.dateTime)

    implementation(libs.androidx.room)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.paging)
}
