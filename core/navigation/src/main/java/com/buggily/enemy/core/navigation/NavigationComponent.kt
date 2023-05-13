package com.buggily.enemy.core.navigation

import androidx.navigation.NavType

sealed class NavigationComponent(
    open val name: String,
) {

    abstract val value: String

    sealed class Path(
        override val name: String,
    ) : NavigationComponent(
        name = name,
    ) {

        data class Static(
            override val name: String,
        ) : Path(
            name = name,
        ) {

            override val value: String
                get() = name
        }

        data class Dynamic(
            override val name: String,
            override val type: NavType<*>,
        ) : Path(
            name = name,
        ), Arguable {

            override val value: String
                get() = "{$name}"

            override val nullable: Boolean
                get() = false
        }
    }

    data class Query(
        override val name: String,
        override val type: NavType<*>,
        override val nullable: Boolean = false,
    ) : NavigationComponent(
        name = name,
    ), Arguable {

        override val value: String
            get() = "$name={$name}"
    }

    interface Arguable {
        val type: NavType<*>
        val nullable: Boolean
    }
}
