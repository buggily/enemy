package com.buggily.enemy.core.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDestination
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed interface NavigationDestination {

    val paths: List<NavigationComponent.Path>
    val queries: List<NavigationComponent.Query>

    private val dynamicPaths: List<NavigationComponent.Path.Dynamic>
        get() = paths.filterIsInstance<NavigationComponent.Path.Dynamic>()

    val arguments: List<NamedNavArgument>
        get() = listOf(
            dynamicPaths,
            queries,
        ).flatten().map {
            navArgument(it.name) {
                type = it.type
                nullable = it.nullable
            }
        }

    val route: String
        get() {
            val separator: String = if (queries.isEmpty()) String() else "?"

            return listOf(
                paths.joinToString(separator = "/") { it.value },
                queries.joinToString(separator = "&") { it.value },
            ).joinToString(separator = separator)
        }

    fun getRoute(vararg args: Pair<String, Any>): String {
        var route: String = route

        args.forEach {
            val key: String = it.first
            val value: Any = it.second

            route = route.replace(
                oldValue = "{$key}",
                newValue = value.toString(),
            )
        }

        return route
    }

    data object Orientation : NavigationDestination {

        override val paths: List<NavigationComponent.Path>
            get() = listOf(NavigationComponent.Path.Static(ORIENTATION))

        override val queries: List<NavigationComponent.Query>
            get() = emptyList()

        private const val ORIENTATION = "orientation"
    }

    data object Browse : NavigationDestination {

        override val paths: List<NavigationComponent.Path>
            get() = listOf(NavigationComponent.Path.Static(BROWSE))

        override val queries: List<NavigationComponent.Query>
            get() = emptyList()

        private const val BROWSE = "browse"
    }

    data object Album : NavigationDestination {

        override val paths: List<NavigationComponent.Path>
            get() = listOf(
                NavigationComponent.Path.Static(ALBUM),
                NavigationComponent.Path.Dynamic(ALBUM_ID, NavType.LongType),
            )

        override val queries: List<NavigationComponent.Query>
            get() = emptyList()

        fun getRoute(
            albumId: Long,
        ): String = getRoute(
            ALBUM_ID to albumId,
        )

        private const val ALBUM = "album"
        const val ALBUM_ID = "albumId"
    }

    object Track {

        val paths: List<NavigationComponent.Path>
            get() = listOf(
                NavigationComponent.Path.Static(TRACK),
                NavigationComponent.Path.Dynamic(TRACK_ID, NavType.LongType),
            )

        data object Picker : NavigationDestination {

            override val paths: List<NavigationComponent.Path>
                get() = Track.paths + listOf(NavigationComponent.Path.Static(PICKER))

            override val queries: List<NavigationComponent.Query>
                get() = emptyList()

            fun getRoute(
                trackId: Long,
            ): String = getRoute(
                TRACK_ID to trackId,
            )

            private const val PICKER = "picker"
            const val TRACK_ID = "trackId"
        }

        data object PlaylistPicker : NavigationDestination {

            override val paths: List<NavigationComponent.Path>
                get() = Track.paths + listOf(NavigationComponent.Path.Static(PLAYLIST))

            override val queries: List<NavigationComponent.Query>
                get() = emptyList()

            fun getRoute(
                trackId: Long,
            ): String = getRoute(
                TRACK_ID to trackId,
            )

            private const val PLAYLIST = Playlist.PLAYLIST
            const val TRACK_ID = Track.TRACK_ID
        }

        const val TRACK = "track"
        const val TRACK_ID = "trackId"
    }

    data object Playlist : NavigationDestination {

        override val paths: List<NavigationComponent.Path>
            get() = listOf(
                NavigationComponent.Path.Static(PLAYLIST),
                NavigationComponent.Path.Dynamic(PLAYLIST_ID, NavType.LongType),
            )

        override val queries: List<NavigationComponent.Query>
            get() = emptyList()

        fun getRoute(
            playlistId: Long,
        ): String = getRoute(
            PLAYLIST_ID to playlistId,
        )

        data object Create : NavigationDestination {

            override val paths: List<NavigationComponent.Path>
                get() = listOf(
                    NavigationComponent.Path.Static(PLAYLIST),
                    NavigationComponent.Path.Static(CREATE),
                )

            override val queries: List<NavigationComponent.Query>
                get() = listOf(NavigationComponent.Query(NAME, NavType.StringType))

            fun getRoute(
                name: String,
            ): String = getRoute(
                NAME to name,
            )

            private const val CREATE = "create"
            const val NAME = "name"
        }

        data object Edit : NavigationDestination {

            override val paths: List<NavigationComponent.Path>
                get() = Playlist.paths + NavigationComponent.Path.Static(EDIT)

            override val queries: List<NavigationComponent.Query>
                get() = emptyList()

            fun getRoute(
                playlistId: Long,
            ): String = getRoute(
                PLAYLIST_ID to playlistId,
            )

            private const val EDIT = "edit"
            const val PLAYLIST_ID = "playlistId"
        }

        data object Picker : NavigationDestination {

            override val paths: List<NavigationComponent.Path>
                get() = Playlist.paths + listOf(NavigationComponent.Path.Static(PICKER))

            override val queries: List<NavigationComponent.Query>
                get() = emptyList()

            fun getRoute(
                playlistId: Long,
            ): String = getRoute(
                PLAYLIST_ID to playlistId,
            )

            private const val PICKER = "picker"
            const val PLAYLIST_ID = "playlistId"
        }

        data object TrackPicker : NavigationDestination {

            override val paths: List<NavigationComponent.Path>
                get() = Playlist.paths + listOf(
                    NavigationComponent.Path.Static(TRACK),
                    NavigationComponent.Path.Dynamic(TRACK_INDEX, NavType.IntType),
                )

            override val queries: List<NavigationComponent.Query>
                get() = listOf(NavigationComponent.Query(TRACK_ID, NavType.LongType))

            fun getRoute(
                trackId: Long,
                playlistId: Long,
                trackIndex: Int,
            ): String = getRoute(
                TRACK_ID to trackId,
                PLAYLIST_ID to playlistId,
                TRACK_INDEX to trackIndex,
            )

            private const val TRACK = Track.TRACK

            const val TRACK_ID = "trackId"
            const val PLAYLIST_ID = "playlistId"
            const val TRACK_INDEX = "trackIndex"
        }

        const val PLAYLIST = "playlist"
        const val PLAYLIST_ID = "playlistId"
    }

    data object Preferences : NavigationDestination {

        override val paths: List<NavigationComponent.Path>
            get() = listOf(NavigationComponent.Path.Static(PREFERENCES))

        override val queries: List<NavigationComponent.Query>
            get() = emptyList()

        private const val PREFERENCES = "preferences"
    }

    data object Controller : NavigationDestination {

        override val paths: List<NavigationComponent.Path>
            get() = listOf(NavigationComponent.Path.Static(CONTROLLER))

        override val queries: List<NavigationComponent.Query>
            get() = emptyList()

        private const val CONTROLLER = "controller"
    }

    companion object {

        fun get(destination: NavDestination?): NavigationDestination? = values.find {
            it.route == destination?.route
        }

        private val values: List<NavigationDestination> = listOf(
            Orientation,
            Browse,
            Album,
            Track.Picker,
            Track.PlaylistPicker,
            Playlist,
            Playlist.Create,
            Playlist.Edit,
            Playlist.Picker,
            Playlist.TrackPicker,
            Preferences,
            Controller,
        )
    }
}
