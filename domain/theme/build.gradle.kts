plugins {
    id("enemy.android.library.domain")
}

android {
    namespace = "com.buggily.enemy.domain.theme"
}

dependencies {
    implementation(project(":data:theme"))
}
