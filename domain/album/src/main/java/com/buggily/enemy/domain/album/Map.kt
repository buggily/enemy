package com.buggily.enemy.domain.album

import com.buggily.enemy.data.album.Album

fun Album.toUi(): AlbumUi = AlbumUi(
    id = id,
    name = name,
    artist = AlbumUi.Artist(
        id = artist.id,
        name = artist.name,
    )
)
