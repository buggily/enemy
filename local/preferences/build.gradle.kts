plugins {
    id("enemy.android.library.local")
}

android {
    namespace = "com.buggily.enemy.local.preferences"
}

dependencies {
    implementation(libs.androidx.datastore.preferences)
}
