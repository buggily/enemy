package com.buggily.enemy.local.preferences

data class LocalTheme(
    val scheme: Scheme,
    val dynamic: Dynamic,
) {

    sealed class Scheme {

        abstract val identity: String

        object Default : Scheme() {

            override val identity: String
                get() = "default"
        }

        object Light : Scheme() {

            override val identity: String
                get() = "light"
        }

        object Dark : Scheme() {

            override val identity: String
                get() = "dark"
        }

        companion object {
            private val values: Set<Scheme> get() = setOf(Light, Dark)
            fun get(identity: String?): Scheme = values.find { identity == it.identity } ?: Default
        }
    }

    sealed class Dynamic {

        abstract val identity: String

        object Default : Dynamic() {

            override val identity: String
                get() = "default"
        }

        object On : Dynamic() {

            override val identity: String
                get() = "on"
        }

        object Off : Dynamic() {

            override val identity: String
                get() = "off"
        }

        companion object {
            private val values: Set<Dynamic> get() = setOf(On, Off)
            fun get(identity: String?): Dynamic = values.find { identity == it.identity } ?: Default
        }
    }
}
