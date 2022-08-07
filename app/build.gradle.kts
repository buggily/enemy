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

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro",
            )
        }
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

        val optIns: List<String> = Build.OptIn.values.map {
            "-opt-in=$it"
        }

        freeCompilerArgs = freeCompilerArgs + optIns
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Version.Compose.COMPILER
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(Dependency.Kotlin.Core.KTX)

    implementation(Dependency.Hilt.IDENTITY)
    kapt(Dependency.Hilt.COMPILER)

    implementation(Dependency.Hilt.Android.NAVIGATION)
    kapt(Dependency.Hilt.Android.COMPILER)

    implementation(Dependency.Paging.IDENTITY)
    implementation(Dependency.Paging.COMPOSE)

    implementation(Dependency.Compose.ACTIVITY)
    implementation(Dependency.Compose.MATERIAL)
    implementation(Dependency.Compose.NAVIGATION)

    implementation(Dependency.Compose.Ui.IDENTITY)
    implementation(Dependency.Compose.Ui.Tooling.PREVIEW)
    debugImplementation(Dependency.Compose.Ui.Tooling.IDENTITY)

    implementation(Dependency.Compose.Lifecycle.IDENTITY)
    implementation(Dependency.Compose.Lifecycle.KTX)
    implementation(Dependency.Compose.Lifecycle.ViewModel.IDENTITY)
    implementation(Dependency.Compose.Lifecycle.ViewModel.KTX)

    implementation(Dependency.Coil.IDENTITY)

    implementation(Dependency.Media.IDENTITY)
    implementation(Dependency.Media.SESSION)
    implementation(Dependency.Media.UI)

    testImplementation(project(":data"))
    testImplementation(Dependency.JUnit.IDENTITY)
    testImplementation(Dependency.Test.COROUTINE)

    androidTestImplementation(Dependency.Test.Android.Compose.JUnit.IDENTITY)
}

kapt {
    correctErrorTypes = true
}
