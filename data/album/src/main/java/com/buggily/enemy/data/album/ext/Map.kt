package com.buggily.enemy.data.album.ext

import com.buggily.enemy.core.model.album.Album as Output
import com.buggily.enemy.local.album.Album as Input

fun Input.map(): Output = Output(
    id = id,
    name = name,
    artist = Output.Artist(
        id = artist.id,
        name = artist.name,
    ),
)
