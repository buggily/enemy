object Dependency {

    object Plugin {
        object Gradle {
            const val ANDROID = "com.android.tools.build:gradle:${Version.Plugin.Gradle.ANDROID}"
            const val HILT = "com.google.dagger:hilt-android-gradle-plugin:${Version.Plugin.Gradle.HILT}"
        }
    }

    object Hilt {
        const val IDENTITY = "com.google.dagger:hilt-android:${Version.Hilt.IDENTITY}"
        const val COMPILER = "com.google.dagger:hilt-android-compiler:${Version.Hilt.IDENTITY}"

        object Android {
            const val NAVIGATION = "androidx.hilt:hilt-navigation:${Version.Hilt.ANDROID}"
            const val COMPILER = "androidx.hilt:hilt-compiler:${Version.Hilt.ANDROID}"
        }
    }

    object Room {
        const val IDENTITY = "androidx.room:room-runtime:${Version.Room.IDENTITY}"
        const val COMPILER = "androidx.room:room-compiler:${Version.Room.IDENTITY}"
        const val COROUTINES = "androidx.room:room-ktx:${Version.Room.IDENTITY}"
        const val PAGING = "androidx.room:room-paging:${Version.Room.PAGING}"
    }

    object Paging {
        const val IDENTITY = "androidx.paging:paging-runtime:${Version.Paging.IDENTITY}"
        const val CORE = "androidx.paging:paging-common:${Version.Paging.IDENTITY}"
        const val COMPOSE = "androidx.paging:paging-compose:${Version.Paging.COMPOSE}"
    }

    object Retrofit {
        const val IDENTITY = "com.squareup.retrofit2:retrofit:${Version.Retrofit.IDENTITY}"
    }

    object Compose {
        const val UI = "androidx.compose.ui:ui:${Version.Compose.IDENTITY}"
        const val ACTIVITY = "androidx.activity:activity-compose:${Version.Compose.ACTIVITY}"
        const val VIEW_MODEL = "androidx.lifecycle:lifecycle-viewmodel-compose:${Version.Compose.VIEW_MODEL}"
        const val MATERIAL = "androidx.compose.material:material:${Version.Compose.IDENTITY}"
        const val NAVIGATION = "androidx.navigation:navigation-compose:${Version.Compose.NAVIGATION}"
    }
}