plugins {
    id("com.android.application")

    id("kotlin-android")
    id("kotlin-kapt")

    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Build.Sdk.COMPILE

    defaultConfig {
        applicationId = Build.ID

        minSdk = Build.Sdk.MIN
        targetSdk = Build.Sdk.TARGET

        versionCode = Version.CODE
        versionName = Version.NAME

        testInstrumentationRunner = Build.RUNNER
    }

    buildFeatures {
        compose = true
    }

    hilt {
        enableAggregatingTask = true
    }

    compileOptions {
        sourceCompatibility = Version.JAVA
        targetCompatibility = Version.JAVA
    }

    kotlinOptions {
        jvmTarget = Version.JAVA.toString()
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Version.Compose.COMPILER
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(Dependency.Hilt.IDENTITY)
    kapt(Dependency.Hilt.COMPILER)

    implementation(Dependency.Hilt.Android.NAVIGATION)
    kapt(Dependency.Hilt.Android.COMPILER)

    implementation(Dependency.Room.IDENTITY)
    kapt(Dependency.Room.COMPILER)

    implementation(Dependency.Room.COROUTINES)
    implementation(Dependency.Room.PAGING)

    implementation(Dependency.Paging.IDENTITY)
    implementation(Dependency.Paging.COMPOSE)

    implementation(Dependency.Retrofit.IDENTITY)

    implementation(Dependency.Compose.UI)
    implementation(Dependency.Compose.ACTIVITY)
    implementation(Dependency.Compose.VIEW_MODEL)
    implementation(Dependency.Compose.MATERIAL)
    implementation(Dependency.Compose.NAVIGATION)
}

kapt {
    correctErrorTypes = true
}
