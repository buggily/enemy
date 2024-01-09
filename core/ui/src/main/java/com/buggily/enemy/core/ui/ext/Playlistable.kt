package com.buggily.enemy.core.ui.ext

import com.buggily.enemy.core.data.Playlistable

val Playlistable.nameText: String
    get() = name

val Playlistable.modificationText: String
    get() = modificationInstant.text
