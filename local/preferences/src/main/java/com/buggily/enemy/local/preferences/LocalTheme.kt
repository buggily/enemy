package com.buggily.enemy.local.preferences

data class LocalTheme(
    val scheme: Scheme,
    val dynamic: Dynamic,
) {

    sealed interface Scheme {

        val identity: String

        data object Default : Scheme {
            override val identity: String = "default"
        }

        data object Light : Scheme {
            override val identity: String = "light"
        }

        data object Dark : Scheme {
            override val identity: String = "dark"
        }

        companion object {
            private val values: Set<Scheme> = setOf(Light, Dark)
            fun get(identity: String?): Scheme = values.find { identity == it.identity } ?: Default
        }
    }

    sealed interface Dynamic {

        val identity: String

        data object Default : Dynamic {
            override val identity: String = "default"
        }

        data object On : Dynamic {
            override val identity: String = "on"
        }

        data object Off : Dynamic {
            override val identity: String = "off"
        }

        companion object {
            private val values: Set<Dynamic> = setOf(On, Off)
            fun get(identity: String?): Dynamic = values.find { identity == it.identity } ?: Default
        }
    }
}
