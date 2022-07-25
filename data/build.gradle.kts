plugins {
    id("com.android.library")

    id("kotlin-android")
    id("kotlin-kapt")
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
}
