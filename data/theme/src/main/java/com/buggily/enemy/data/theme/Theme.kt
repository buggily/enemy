package com.buggily.enemy.data.theme

data class Theme(
    val scheme: Scheme,
    val dynamic: Dynamic
) {

    sealed interface Scheme {
        data object Default : Scheme
        data object Light : Scheme
        data object Dark : Scheme
    }

    sealed interface Dynamic {
        data object On : Dynamic
        data object Off : Dynamic
    }
}
