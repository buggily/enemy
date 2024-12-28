plugins {
    id("enemy.kotlin.library.local")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.androidx.datastore.core)
    implementation(libs.kotlinx.serialization)
}
