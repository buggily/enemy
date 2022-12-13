plugins {
    id("enemy.android.library")
    id("enemy.android.library.local")
    id("enemy.android.hilt")
}

android {
    namespace = "com.buggily.enemy.local.preferences"
}

dependencies {
    implementation(libs.androidx.datastore.preferences)
}
