package com.buggily.enemy.core.navigation

import androidx.navigation.NavType

sealed interface NavigationComponent {

    val name: String
    val value: String

    sealed interface Path : NavigationComponent {

        data class Static(
            override val name: String,
        ) : Path {

            override val value: String
                get() = name
        }

        data class Dynamic(
            override val name: String,
            override val type: NavType<*>,
        ) : Path, Arguable {

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
    ) : NavigationComponent, Arguable {

        override val value: String
            get() = "$name={$name}"
    }

    interface Arguable {
        val type: NavType<*>
        val nullable: Boolean
    }
}
