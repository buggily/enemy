package com.buggily.enemy.data.album.ext

import com.buggily.enemy.core.model.album.Album
import com.buggily.enemy.external.album.Album as ExternalAlbum

fun ExternalAlbum.map(): Album = Album(
    id = id,
    name = name,
    artist = Album.Artist(
        id = artist.id,
        name = artist.name,
    ),
)
