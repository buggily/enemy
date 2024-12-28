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
include(":core:ext")
include(":core:data")
include(":core:domain")
include(":core:query")
include(":core:paging")
include(":core:local")
include(":core:navigation")
include(":core:controller")

include(":core:test")

include(":feature:orientation")
include(":feature:preferences")
include(":feature:controller")
include(":feature:browse")
include(":feature:recent")

include(":feature:albums")
include(":feature:album")

include(":feature:tracks")
include(":feature:track")

include(":feature:playlists")
include(":feature:playlist")

include(":domain:album")
include(":domain:track")
include(":domain:theme")
include(":domain:playlist")
include(":domain:playlistWithTracks")
include(":domain:resume")

include(":domain:navigation")
include(":domain:controller")

include(":data:theme")
include(":data:album")
include(":data:track")
include(":data:playlist")
include(":data:playlistWithTracks")
include(":data:resume")

include(":local:track")
include(":local:preferences")
include(":local:playlist")
include(":local:playlist:track")
include(":local:resume")

include(":external:album")
include(":external:track")
