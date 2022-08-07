object Dependency {

    object Kotlin {
        object Core {
            const val KTX = "androidx.core:core-ktx:${Version.Kotlin.CORE}"
        }
    }

    object Hilt {
        const val IDENTITY = "com.google.dagger:hilt-android:${Version.Hilt.IDENTITY}"
        const val CORE = "com.google.dagger:hilt-core:${Version.Hilt.IDENTITY}"
        const val COMPILER = "com.google.dagger:hilt-android-compiler:${Version.Hilt.IDENTITY}"

        object Android {
            const val NAVIGATION = "androidx.hilt:hilt-navigation-compose:${Version.Hilt.ANDROID}"
            const val COMPILER = "androidx.hilt:hilt-compiler:${Version.Hilt.ANDROID}"
        }
    }

    object DataStore {
        object Preferences {
            const val IDENTITY = "androidx.datastore:datastore-preferences:${Version.DataStore.IDENTITY}"
        }
    }

    object Paging {
        const val IDENTITY = "androidx.paging:paging-runtime:${Version.Paging.IDENTITY}"
        const val CORE = "androidx.paging:paging-common:${Version.Paging.IDENTITY}"
        const val COMPOSE = "androidx.paging:paging-compose:${Version.Paging.COMPOSE}"
    }

    object Compose {
        const val ACTIVITY = "androidx.activity:activity-compose:${Version.Compose.ACTIVITY}"
        const val MATERIAL = "androidx.compose.material3:material3:${Version.Compose.MATERIAL}"
        const val NAVIGATION = "androidx.navigation:navigation-compose:${Version.Compose.NAVIGATION}"

        object Ui {

            const val IDENTITY = "androidx.compose.ui:ui:${Version.Compose.IDENTITY}"

            object Tooling {
                const val IDENTITY =  "androidx.compose.ui:ui-tooling:${Version.Compose.Tooling.IDENTITY}"
                const val PREVIEW = "androidx.compose.ui:ui-tooling-preview:${Version.Compose.Tooling.IDENTITY}"
            }
        }

        object Lifecycle {

            const val IDENTITY = "androidx.lifecycle:lifecycle-runtime-compose:${Version.Compose.LIFECYCLE}"
            const val KTX = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.Compose.LIFECYCLE}"

            object ViewModel {
                const val IDENTITY = "androidx.lifecycle:lifecycle-viewmodel-compose:${Version.Compose.LIFECYCLE}"
                const val KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.Compose.LIFECYCLE}"
            }
        }
    }

    object Coil {
        const val IDENTITY = "io.coil-kt:coil-compose:${Version.Coil.IDENTITY}"
    }

    object Media {
        const val IDENTITY = "androidx.media3:media3-exoplayer:${Version.Media.IDENTITY}"
        const val SESSION = "androidx.media3:media3-session:${Version.Media.IDENTITY}"
        const val UI = "androidx.media3:media3-ui:${Version.Media.IDENTITY}"
    }

    object JUnit {
        const val IDENTITY = "junit:junit:${Version.JUnit.IDENTITY}"
    }

    object Test {

        const val COROUTINE = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.Test.COROUTINE}"

        object Android {
            object Compose {
                object JUnit {
                    const val IDENTITY = "androidx.compose.ui:ui-test-junit4:${Version.Compose.IDENTITY}"
                }
            }
        }
    }
}
