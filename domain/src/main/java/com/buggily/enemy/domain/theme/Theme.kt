package com.buggily.enemy.domain.theme

sealed class Theme {

    object Default : Theme()
    object Light : Theme()
    object Dark : Theme()

    sealed class Dynamic {
        object Default : Dynamic()
        object On : Dynamic()
        object Off : Dynamic()
    }
}
