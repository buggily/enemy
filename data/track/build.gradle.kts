plugins {
    id("enemy.android.library.data")
}

android {
    namespace = "com.buggily.enemy.data.track"
}

dependencies {
    implementation(project(":local:track"))
    implementation(project(":external:track"))

    implementation(project(":core:domain"))
    implementation(project(":core:query"))
    implementation(project(":core:paging"))

    implementation(libs.androidx.paging.core)
}
