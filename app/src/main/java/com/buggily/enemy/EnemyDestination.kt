package com.buggily.enemy

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import androidx.navigation.NavDestination
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.buggily.enemy.feature.album.AlbumViewModel

sealed class EnemyDestination {

    sealed class Argument {

        abstract val identity: String
        abstract val expression: String
        abstract val type: NavType<*>

        class Required(
            override val identity: String,
            override val expression: String,
            override val type: NavType<*>,
        ) : Argument()

        class Optional(
            override val identity: String,
            override val expression: String,
            override val type: NavType<*>,
        ) : Argument()

        val isOptional: Boolean
            get() = this is Optional
    }

    abstract val identity: String
    abstract val argumentIdentities: List<Argument>

    val route: String
        get() = getRoute(argumentIdentities)

    val deepLinks: List<NavDeepLink>
        get() = emptyList()

    val arguments: List<NamedNavArgument>
        get() = argumentIdentities.map {
            navArgument(it.identity) {
                type = it.type
                nullable = it.isOptional
            }
        }

    protected fun getRoute(arguments: List<Argument>): String {
        val requiredArguments: List<Argument.Required> = arguments.mapNotNull { it as? Argument.Required }
        val optionalArguments: List<Argument.Optional> = arguments.mapNotNull { it as? Argument.Optional }

        val requiredArgString: String = requiredArguments.joinToString(String()) {
            "/${it.expression}"
        }

        val optionalArgsString: String = optionalArguments.joinToString(String()) {
            "?${it.identity}=${it.expression}"
        }

        return listOf(
            identity,
            requiredArgString,
            optionalArgsString,
        ).joinToString(String())
    }

    object Orientation : EnemyDestination() {

        override val identity: String
            get() = "orientation"

        override val argumentIdentities: List<Argument>
            get() = emptyList()
    }

    object Albums : EnemyDestination() {

        override val identity: String
            get() = "albums"

        override val argumentIdentities: List<Argument>
            get() = emptyList()
    }

    object Album : EnemyDestination() {

        override val identity: String
            get() = "album"

        override val argumentIdentities: List<Argument>
            get() = getArguments("{$id}")

        fun getRoute(id: Long): String = getRoute(getArguments(id))

        private fun getArguments(id: Any): List<Argument> = listOf(
            Argument.Required(
                identity = Album.id,
                expression = id.toString(),
                type = NavType.LongType,
            ),
        )

        private const val id = AlbumViewModel.id
    }

    object Settings : EnemyDestination() {

        override val identity: String
            get() = "settings"

        override val argumentIdentities: List<Argument>
            get() = emptyList()
    }

    companion object {
        fun get(destination: NavDestination?): EnemyDestination? = listOf(
            Orientation,
            Albums,
            Album,
            Settings,
        ).find { it.route == destination?.route }
    }
}
