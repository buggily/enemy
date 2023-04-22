package com.buggily.enemy.data.theme

data class Theme(
    val scheme: Scheme,
    val dynamic: Dynamic
) {

    sealed class Scheme {
        object Default : Scheme()
        object Light : Scheme()
        object Dark : Scheme()
    }

    sealed class Dynamic {
        object On : Dynamic()
        object Off : Dynamic()
    }

    companion object {
        val default: Theme
            get() = Theme(
                scheme = Scheme.Default,
                dynamic = Dynamic.On,
            )
    }
}
