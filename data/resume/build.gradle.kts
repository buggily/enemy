plugins {
    id("enemy.android.library.data")
}

android {
    namespace = "com.buggily.enemy.data.resume"
}

dependencies {
    implementation(project(":local:resume"))
    implementation(libs.androidx.datastore)
}
