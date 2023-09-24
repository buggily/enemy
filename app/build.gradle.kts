import java.util.Properties

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

    val keyStorePropertiesFile: File = rootProject.file("keystore.properties")
    val keyStoreProperties: Properties = Properties().apply {
        load(keyStorePropertiesFile.inputStream())
    }

    signingConfigs {
        create("release") {
            storeFile = file(keyStoreProperties.getProperty("storeFileName"))
            storePassword = keyStoreProperties.getProperty("storePassword")

            keyAlias = keyStoreProperties.getProperty("keyAlias")
            keyPassword = keyStoreProperties.getProperty("keyPassword")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro",
            )
        }

        forEach {
            it.signingConfig = signingConfigs.getByName(it.name)
        }
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:ext"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:navigation"))
    implementation(project(":core:controller"))

    implementation(project(":feature:orientation"))
    implementation(project(":feature:preferences"))
    implementation(project(":feature:controller"))
    implementation(project(":feature:browse"))

    implementation(project(":feature:albums"))
    implementation(project(":feature:album"))

    implementation(project(":feature:tracks"))
    implementation(project(":feature:track"))

    implementation(project(":feature:playlists"))
    implementation(project(":feature:playlist"))

    implementation(project(":domain:album"))
    implementation(project(":domain:track"))
    implementation(project(":domain:playlist"))
    implementation(project(":domain:navigation"))
    implementation(project(":domain:controller"))
    implementation(project(":domain:theme"))

    implementation(project(":data:album"))
    implementation(project(":data:track"))
    implementation(project(":data:playlist"))
    implementation(project(":data:theme"))

    with(libs) {
        implementation(kotlin)
        implementation(kotlinx.dateTime)

        implementation(kotlinx.coroutines.core)
        implementation(kotlinx.coroutines.android)

        implementation(androidx.core.ktx)
        implementation(androidx.core.splashscreen)

        implementation(androidx.paging)
        implementation(androidx.paging.compose)

        implementation(androidx.media.ui)
        implementation(androidx.media.player)
        implementation(androidx.media.session)

        implementation(coil)
        implementation(coil.compose)
    }
}
