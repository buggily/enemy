plugins {
    id("enemy.android.library")
    id("enemy.android.library.data")
}

android {
    namespace = "com.buggily.enemy.data.theme"
}

dependencies {
    implementation(project(":local:preferences"))
    implementation(libs.androidx.datastore.preferences.core)
}
