package com.buggily.enemy.data.album

import com.buggily.enemy.external.album.ExternalAlbum

fun ExternalAlbum.to(): Album = Album(
    id = id,
    name = name,
    artist = Album.Artist(
        id = artist.id,
        name = artist.name,
    ),
)
