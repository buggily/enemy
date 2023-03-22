package com.buggily.enemy.core.ui.ext

import com.buggily.enemy.core.model.playlist.Playlist

val Playlist.nameText: String
    get() = name

val Playlist.modificationText: String
    get() = modificationInstant.text
