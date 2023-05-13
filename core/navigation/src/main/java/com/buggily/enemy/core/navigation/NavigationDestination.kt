package com.buggily.enemy.core.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDestination
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavigationDestination {

    abstract val paths: List<NavigationComponent.Path>
    abstract val queries: List<NavigationComponent.Query>

    private val dynamicPaths: List<NavigationComponent.Path.Dynamic>
        get() = paths.filterIsInstance(NavigationComponent.Path.Dynamic::class.java)

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

    object Orientation : NavigationDestination() {

        override val paths: List<NavigationComponent.Path>
            get() = listOf(NavigationComponent.Path.Static(orientation))

        override val queries: List<NavigationComponent.Query>
            get() = emptyList()

        private const val orientation = "orientation"
    }

    object Browse : NavigationDestination() {

        override val paths: List<NavigationComponent.Path>
            get() = listOf(NavigationComponent.Path.Static(browse))

        override val queries: List<NavigationComponent.Query>
            get() = emptyList()

        private const val browse = "browse"
    }

    object Album : NavigationDestination() {

        override val paths: List<NavigationComponent.Path>
            get() = listOf(
                NavigationComponent.Path.Static(album),
                NavigationComponent.Path.Dynamic(albumId, NavType.LongType),
            )

        override val queries: List<NavigationComponent.Query>
            get() = emptyList()

        fun getRoute(
            albumId: Long,
        ): String = getRoute(
            Album.albumId to albumId,
        )

        private const val album = "album"
        const val albumId = "albumId"
    }

    object Track {

        val paths: List<NavigationComponent.Path>
            get() = listOf(
                NavigationComponent.Path.Static(track),
                NavigationComponent.Path.Dynamic(trackId, NavType.LongType),
            )

        object Picker : NavigationDestination() {

            override val paths: List<NavigationComponent.Path>
                get() = Track.paths + listOf(NavigationComponent.Path.Static(picker))

            override val queries: List<NavigationComponent.Query>
                get() = emptyList()

            fun getRoute(
                trackId: Long,
            ): String = getRoute(
                Picker.trackId to trackId,
            )

            private const val picker = "picker"
            const val trackId = Track.trackId
        }

        object PlaylistPicker : NavigationDestination() {

            override val paths: List<NavigationComponent.Path>
                get() = Track.paths + listOf(NavigationComponent.Path.Static(playlist))

            override val queries: List<NavigationComponent.Query>
                get() = emptyList()

            fun getRoute(
                trackId: Long,
            ): String = getRoute(
                PlaylistPicker.trackId to trackId,
            )

            private const val playlist = Playlist.playlist
            const val trackId = Track.trackId
        }

        const val track = "track"
        const val trackId = "trackId"
    }

    object Playlist : NavigationDestination() {

        override val paths: List<NavigationComponent.Path>
            get() = listOf(
                NavigationComponent.Path.Static(playlist),
                NavigationComponent.Path.Dynamic(playlistId, NavType.LongType),
            )

        override val queries: List<NavigationComponent.Query>
            get() = emptyList()

        fun getRoute(
            playlistId: Long,
        ): String = getRoute(
            Playlist.playlistId to playlistId,
        )

        object Create : NavigationDestination() {

            override val paths: List<NavigationComponent.Path>
                get() = listOf(
                    NavigationComponent.Path.Static(playlist),
                    NavigationComponent.Path.Static(create),
                )

            override val queries: List<NavigationComponent.Query>
                get() = listOf(NavigationComponent.Query(name, NavType.StringType))

            fun getRoute(
                name: String,
            ): String = getRoute(
                Create.name to name,
            )

            private const val create = "create"
            const val name = "name"
        }

        object Picker : NavigationDestination() {

            override val paths: List<NavigationComponent.Path>
                get() = Playlist.paths + listOf(NavigationComponent.Path.Static(picker))

            override val queries: List<NavigationComponent.Query>
                get() = emptyList()

            fun getRoute(
                playlistId: Long,
            ): String = getRoute(
                Picker.playlistId to playlistId,
            )

            private const val picker = "picker"
            const val playlistId = "playlistId"
        }

        object TrackPicker : NavigationDestination() {

            override val paths: List<NavigationComponent.Path>
                get() = Playlist.paths + listOf(
                    NavigationComponent.Path.Static(track),
                    NavigationComponent.Path.Dynamic(trackIndex, NavType.IntType),
                )

            override val queries: List<NavigationComponent.Query>
                get() = emptyList()

            fun getRoute(
                playlistId: Long,
                trackIndex: Int,
            ): String = getRoute(
                TrackPicker.playlistId to playlistId,
                TrackPicker.trackIndex to trackIndex,
            )

            private const val track = Track.track
            const val playlistId = Playlist.playlistId
            const val trackIndex = "trackIndex"
        }

        const val playlist = "playlist"
        const val playlistId = "playlistId"
    }

    object Preferences : NavigationDestination() {

        override val paths: List<NavigationComponent.Path>
            get() = listOf(NavigationComponent.Path.Static(preferences))

        override val queries: List<NavigationComponent.Query>
            get() = emptyList()

        private const val preferences = "preferences"
    }

    object Controller : NavigationDestination() {

        override val paths: List<NavigationComponent.Path>
            get() = listOf(NavigationComponent.Path.Static(controller))

        override val queries: List<NavigationComponent.Query>
            get() = emptyList()

        private const val controller = "controller"
    }

    companion object {

        val startDestination: NavigationDestination
            get() = Browse

        fun get(destination: NavDestination?): NavigationDestination? = values.find {
            it.route == destination?.route
        }

        private val values: List<NavigationDestination>
            get() = listOf(
                Orientation,
                Browse,
                Album,
                Track.Picker,
                Track.PlaylistPicker,
                Playlist,
                Playlist.Create,
                Playlist.Picker,
                Playlist.TrackPicker,
                Preferences,
                Controller,
            )
    }
}
