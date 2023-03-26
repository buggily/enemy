@file:Suppress("UnstableApiUsage")

include(":external:track")


pluginManagement {
    includeBuild("builder")

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "enemy"
include(":app")

include(":core:ui")
include(":core:model")
include(":core:domain")
include(":core:query")
include(":core:paging")
include(":core:local")
include(":core:ext")
include(":core:navigation")
include(":core:controller")

include(":core:test")

include(":domain:album")
include(":domain:track")
include(":domain:theme")
include(":domain:playlist")
include(":domain:navigation")
include(":domain:controller")

include(":feature:orientation")
include(":feature:preferences")
include(":feature:controller")
include(":feature:browse")

include(":feature:albums")
include(":feature:album")

include(":feature:tracks")

include(":feature:playlists")
include(":feature:playlist")

include(":data:album")
include(":data:track")
include(":data:playlist")
include(":data:theme")

include(":local:track")
include(":local:preferences")
include(":local:playlist")

include(":external:album")
include(":external:track")
