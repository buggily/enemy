package com.buggily.enemy.core.ui.ext

import com.buggily.enemy.core.model.album.Album

val Album.nameText: String
    get() = name

val Album.artistText: String
    get() = artist.name
