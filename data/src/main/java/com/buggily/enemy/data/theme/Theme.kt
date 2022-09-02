package com.buggily.enemy.data.theme

sealed class Theme {

    abstract val identity: String

    object Default : Theme() {

        override val identity: String
            get() = "default"
    }

    object Light : Theme() {

        override val identity: String
            get() = "light"
    }

    object Dark : Theme() {

        override val identity: String
            get() = "dark"
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
            fun get(identity: String?): Dynamic = listOf(
                On,
                Off,
            ).find { identity == it.identity } ?: Default
        }
    }

    companion object {
        fun get(identity: String?): Theme = listOf(
            Light,
            Dark,
        ).find { identity == it.identity } ?: Default
    }
}
