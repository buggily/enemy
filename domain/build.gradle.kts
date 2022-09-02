
import com.google.protobuf.gradle.builtins
import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
    id("com.android.library")

    kotlin("android")
    kotlin("kapt")

    id("dagger.hilt.android.plugin")
    id("com.google.protobuf")
}

android {
    compileSdk = Build.Sdk.COMPILE

    defaultConfig {
        minSdk = Build.Sdk.MIN
        targetSdk = Build.Sdk.TARGET
    }

    compileOptions {
        sourceCompatibility = Version.JAVA
        targetCompatibility = Version.JAVA
    }

    kotlinOptions {
        jvmTarget = Version.JAVA.toString()
    }
}

dependencies {
    implementation(project(":data"))

    implementation(Dependency.Hilt.IDENTITY)
    kapt(Dependency.Hilt.COMPILER)

    implementation(Dependency.Paging.CORE)
    implementation(Dependency.DataStore.IDENTITY)
    implementation(Dependency.DataStore.Proto.JAVA)
    implementation(Dependency.DataStore.Proto.KOTLIN)
}

kapt {
    correctErrorTypes = true
}

protobuf {
    protoc {
        artifact = Dependency.DataStore.Proto.IDENTITY
    }

    generateProtoTasks {
        all().forEach {
            it.builtins {
                id("java") { option("lite") }
                id("kotlin") { option("lite") }
            }
        }
    }
}
