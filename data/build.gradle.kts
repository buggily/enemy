plugins {
    id("com.android.library")

    id("kotlin-android")
    id("kotlin-kapt")

    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Build.Sdk.COMPILE

    defaultConfig {
        minSdk = Build.Sdk.MIN
        targetSdk = Build.Sdk.TARGET
    }
}

dependencies {
    implementation(Dependency.Hilt.IDENTITY)
    kapt(Dependency.Hilt.COMPILER)

    implementation(Dependency.Paging.CORE)
    implementation(Dependency.DataStore.Preferences.IDENTITY)
}
