package com.buggily.enemy.core.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDestination
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavigationDestination {

    sealed class Argument(
        open val name: String,
    ) {

        open val value: String
            get() = "{$name}"

        sealed class Config(
            override val name: String,
            open val type: NavType<*>,
            open val isNullable: Boolean,
        ) : Argument(
            name = name,
        ) {

            open class Page(
                override val name: String,
                override val type: NavType<*>,
            ) : Config(
                name = name,
                type = type,
                isNullable = false,
            ) {

                override val value: String
                    get() = "/${super.value}"
            }

            open class Query(
                override val name: String,
                override val type: NavType<*>,
                override val isNullable: Boolean,
            ) : Config(
                name = name,
                type = type,
                isNullable = isNullable,
            ) {

                override val value: String
                    get() = "$name=${super.value}"
            }
        }

        sealed class Expression(
            override val name: String,
            open val expression: String?,
        ) : Argument(
            name = name,
        ) {

            class Page(
                override val name: String,
                override val expression: String,
            ) : Expression(
                name = name,
                expression = expression,
            )

            class Query(
                override val name: String,
                override val expression: String?,
            ) : Expression(
                name = name,
                expression = expression,
            )
        }
    }

    abstract val path: String

    open val pageArgumentConfigs: List<Argument.Config.Page>
        get() = emptyList()

    open val queryArgumentConfigs: Set<Argument.Config.Query>
        get() = emptySet()

    val arguments: List<NamedNavArgument>
        get() = argumentConfigs.map {
            navArgument(it.name) {
                type = it.type
                nullable = it.isNullable
            }
        }

    private val argumentConfigs: List<Argument.Config>
        get() = pageArgumentConfigs + queryArgumentConfigs

    val route: String
        get() = listOfNotNull(
            path,
            pageArgumentConfigsString,
            queryArgumentConfigsString,
        ).joinToString(String())

    private val pageArgumentConfigsString: String?
        get() = getArgumentConfigsString(
            prefix = "/",
            separator = "/",
            argumentConfigs = pageArgumentConfigs,
        )

    private val queryArgumentConfigsString: String?
        get() = getArgumentConfigsString(
            prefix = "?",
            separator = "&",
            argumentConfigs = queryArgumentConfigs.toList(),
        )

    private fun getArgumentConfigsString(
        prefix: String,
        separator: String,
        argumentConfigs: List<Argument.Config>,
    ): String? = argumentConfigs.takeUnless { it.isEmpty() }?.let {
        prefix + it.joinToString(separator = separator) { argumentConfig: Argument.Config ->
            argumentConfig.value
        }
    }

    protected fun getRoute(
        pageArgumentExpressions: List<Argument.Expression.Page>,
        queryArgumentExpressions: Set<Argument.Expression.Query>,
    ): String {
        var route: String = route

        listOf(
            pageArgumentExpressions,
            queryArgumentExpressions,
        ).flatten().forEach {
            route = with(it) { route.replace(value, expression.toString()) }
        }

        return route
    }

    object Orientation : NavigationDestination() {

        override val path: String
            get() = "orientation"
    }

    object Browse : NavigationDestination() {

        override val path: String
            get() = "browse"
    }

    object Album : NavigationDestination() {

        override val path: String
            get() = "album"

        override val pageArgumentConfigs: List<Argument.Config.Page>
            get() = listOf(idPageArgumentConfig)

        fun getRoute(id: Long): String {
            val idPageArgumentExpression = Argument.Expression.Page(
                name = idPageArgumentConfig.name,
                expression = id.toString(),
            )

            return getRoute(
                pageArgumentExpressions = listOf(idPageArgumentExpression),
                queryArgumentExpressions = emptySet(),
            )
        }

        private val idPageArgumentConfig: Argument.Config.Page
            get() = Argument.Config.Page(
                name = id,
                type = NavType.LongType,
            )

        const val id = "id"
    }

    object Preferences : NavigationDestination() {

        override val path: String
            get() = "preferences"
    }

    object Controller : NavigationDestination() {

        override val path: String
            get() = "controller"
    }

    object Playlist : NavigationDestination() {

        override val path: String
            get() = "playlist"

        override val pageArgumentConfigs: List<Argument.Config.Page>
            get() = listOf(idPageArgumentConfig)

        fun getRoute(id: Long): String {
            val idPageArgumentExpression = Argument.Expression.Page(
                name = idPageArgumentConfig.name,
                expression = id.toString(),
            )

            return getRoute(
                pageArgumentExpressions = listOf(idPageArgumentExpression),
                queryArgumentExpressions = emptySet(),
            )
        }

        private val idPageArgumentConfig: Argument.Config.Page
            get() = Argument.Config.Page(
                name = id,
                type = NavType.LongType,
            )

        object Create : NavigationDestination() {

            override val path: String
                get() = "createPlaylist"

            override val queryArgumentConfigs: Set<Argument.Config.Query>
                get() = setOf(nameQueryArgumentConfig)

            fun getRoute(name: String): String {
                val nameQueryArgumentExpression = Argument.Expression.Query(
                    name = nameQueryArgumentConfig.name,
                    expression = name,
                )

                return getRoute(
                    pageArgumentExpressions = emptyList(),
                    queryArgumentExpressions = setOf(nameQueryArgumentExpression),
                )
            }

            private val nameQueryArgumentConfig: Argument.Config.Query
                get() = Argument.Config.Query(
                    name = name,
                    type = NavType.StringType,
                    isNullable = false,
                )

            const val name = "name"
        }

        const val id = "id"
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
                Preferences,
                Controller,
            )
    }
}
