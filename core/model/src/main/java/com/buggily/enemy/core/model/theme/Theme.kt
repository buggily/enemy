package com.buggily.enemy.core.model.theme

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

        object Default : Dynamic()
        object On : Dynamic()
        object Off : Dynamic()

        val isOn: Boolean
            get() = this !is Off
    }

    companion object {
        val default: Theme
            get() = Theme(
                scheme = Scheme.Default,
                dynamic = Dynamic.Default,
            )
    }
}
