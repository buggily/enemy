package com.buggily.enemy.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDestination
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.buggily.enemy.feature.album.AlbumViewModel

sealed class EnemyDestination {

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
                    get() = "/" + super.value
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
                    get() = "$name=" + super.value
            }
        }

        sealed class Expression(
            override val name: String,
            open val expression: String,
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
                override val expression: String,
            ) : Expression(
                name = name,
                expression = expression,
            )
        }
    }

    abstract val path: String

    open val pageArgumentConfigs: List<Argument.Config.Page> = emptyList()
    open val queryArgumentConfigs: List<Argument.Config.Query> = emptyList()

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
        get() = pageArgumentConfigs.takeUnless { it.isEmpty() }?.let {
            "/" + it.joinToString("/") { pageArgumentConfig: Argument.Config.Page ->
                pageArgumentConfig.value
            }
        }

    private val queryArgumentConfigsString: String?
        get() = queryArgumentConfigs.takeUnless { it.isEmpty() }?.let {
            "?" + it.joinToString("&") { queryArgumentConfig: Argument.Config.Query ->
                queryArgumentConfig.value
            }
        }

    protected fun getRoute(
        pageArgumentExpressions: List<Argument.Expression.Page>,
        queryArgumentExpressions: List<Argument.Expression.Query>,
    ): String = getRoute(
        argumentExpressions = pageArgumentExpressions + queryArgumentExpressions,
    )

    private fun getRoute(argumentExpressions: List<Argument.Expression>): String {
        var route: String = route

        argumentExpressions.forEach {
            route = with(it) { route.replace(value, expression) }
        }

        return route
    }

    object Orientation : EnemyDestination() {

        override val path: String
            get() = "orientation"
    }

    object Albums : EnemyDestination() {

        override val path: String
            get() = "albums"
    }

    object Album : EnemyDestination() {

        override val path: String
            get() = "album"

        override val pageArgumentConfigs: List<Argument.Config.Page>
            get() = listOf(idPageArgumentConfig)

        fun getRoute(id: Long): String {
            val idPageArgumentExpression: Argument.Expression.Page = Argument.Expression.Page(
                name = idPageArgumentConfig.name,
                expression = id.toString(),
            )

            return getRoute(
                pageArgumentExpressions = listOf(idPageArgumentExpression),
                queryArgumentExpressions = emptyList(),
            )
        }

        private val idPageArgumentConfig: Argument.Config.Page
            get() = Argument.Config.Page(
                name = AlbumViewModel.id,
                type = NavType.LongType,
            )
    }

    object Preferences : EnemyDestination() {

        override val path: String
            get() = "preferences"
    }

    object Controller : EnemyDestination() {

        override val path: String
            get() = "controller"
    }

    companion object {
        fun get(destination: NavDestination?): EnemyDestination? = listOf(
            Albums,
            Orientation,
            Album,
            Preferences,
            Controller,
        ).find { it.route == destination?.route }
    }
}
