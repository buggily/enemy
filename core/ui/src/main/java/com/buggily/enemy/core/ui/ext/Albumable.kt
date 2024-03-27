package com.buggily.enemy.core.ui.ext

import com.buggily.enemy.core.data.Albumable

val Albumable.nameText: String
    get() = name

val Albumable.artistText: String
    get() = artist.name
