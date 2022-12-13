plugins {
    id("enemy.android.application")
    id("enemy.android.application.compose")
    id("enemy.android.hilt")
}

android {

    namespace = "com.buggily.enemy"

    defaultConfig {
        applicationId = namespace

        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))

    implementation(project(":feature:orientation"))
    implementation(project(":feature:album"))
    implementation(project(":feature:preferences"))

    implementation(project(":domain:album"))
    implementation(project(":domain:track"))
    implementation(project(":domain:theme"))

    implementation(libs.kotlin)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)

    implementation(libs.androidx.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.windowSizeClass)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)

    implementation(libs.androidx.media)
    implementation(libs.androidx.media.ui)
    implementation(libs.androidx.media.session)

    implementation(libs.coil)
    implementation(libs.coil.compose)
}
