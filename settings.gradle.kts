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

include(":core:test")

include(":domain:album")
include(":domain:track")
include(":domain:theme")

include(":feature:orientation")
include(":feature:preferences")
include(":feature:controller")

include(":feature:albums")
include(":feature:album")

include(":data:album")
include(":data:track")
include(":data:theme")

include(":local:album")
include(":local:track")
include(":local:preferences")
